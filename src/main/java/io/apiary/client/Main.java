package io.apiary.client;

import io.apiary.client.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Salvatore Rapisarda
 *
 *
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

        public static void main(String args[]) {

            logger.info("Client apiray starting.");
            ApplicationConf conf = ApplicationConf.getInstance();
            ApiClient apiClient = new ApiClient(conf.getApiUrl());

            try {
                //
                logger.info("authenticate by getting token.");
                TokenApi token  = apiClient.authenticate(new User(conf.getUsername(), conf.getApikey()));
                //
                logger.info("add recipient.");
                RecipientApi recipientApi = apiClient.addRecipient(token.getToken(), "Salvatore_" + new Date().getTime());
                //
                logger.info("create payment.");
                PaymentApi createPayment = new PaymentApi();
                Payment payment = new Payment();
                payment.setAmount(10.5);
                payment.setCurrency("GPB");
                payment.setRecipient_id(recipientApi.getRecipient().getId());
                createPayment.setPayment(payment);
                PaymentApi payment1 = apiClient.createPayment(token.getToken(), createPayment);
                //
                logger.info("checking payment");
                PaymentApi paymentApi = apiClient.checkPayment(payment1);
                if (paymentApi != null )
                    logger.info("the payment: \n" + paymentApi + "\nhas been correctly issued." );
                else
                    logger.warn("the payment check failed!!" );


            } catch (Exception e) {
                logger.error("An error occurred during the client executions steps!!");
            }

        }

}
