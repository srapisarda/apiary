package io.apiary.client;

import io.apiary.client.dao.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

/**
 * Created by Salvatore Rapisarda
 *
 * on 08/07/2017.
 */
public class ApiClientTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ApiClient apiClient;
    ApplicationConf conf =  ApplicationConf.getInstance();

    public ApiClientTest() throws MalformedURLException {
        apiClient  = new ApiClient(conf.getApiUrl());
    }

    private TokenApi authentication() throws Exception {
        return apiClient.authenticate( new User(conf.getUsername(), conf.getApikey()));
    }

    @Test
    public void authenticationTest() throws Exception{
        logger.info("authentication test");
        Assert.assertNotNull(authentication().getToken());
    }

    @Test
    public void  addRecipientTest() throws Exception{
        logger.info("add recipient test");
        String expected  = "Jake McFriend";
        String token = authentication().getToken();
        RecipientApi recipientApi = apiClient.addRecipient(token, expected );
        Assert.assertEquals(expected, recipientApi.getRecipient().getName());

    }

    @Test
    public void paymentTest() throws Exception {
        logger.info("payment test");
        String token = authentication().getToken();
        // add recipient
        RecipientApi recipientApi = apiClient.addRecipient(token,  "Jake McFriend" );

        PaymentApi createPayment = new PaymentApi();
        Payment payment = new Payment();
        payment.setAmount(10.5);
        payment.setCurrency("GPB");
        payment.setRecipient_id(recipientApi.getRecipient().getId());
        createPayment.setPayment(payment );
        // Create a payment
        PaymentApi payment1 = apiClient.createPayment(token, createPayment);


        Assert.assertNotNull(payment1);
        Assert.assertNotNull(payment1.getPayment().getRecipient_id());
        Assert.assertEquals("processing", payment1.getPayment().getStatus() );
    }

    @Test
    public void checkPaymentTest() throws Exception {
        logger.info("check payment test");
        String token = authentication().getToken();
        RecipientApi recipientApi = apiClient.addRecipient(token,  "Jake McFriend" );

        PaymentApi createPayment = new PaymentApi();
        Payment payment = new Payment();
        payment.setAmount(10.5);
        payment.setCurrency("GPB");
        payment.setRecipient_id(recipientApi.getRecipient().getId());
        createPayment.setPayment(payment );
        // Create a payment
        logger.info("create payment ." );
        PaymentApi paymentApi = apiClient.createPayment(token, createPayment);

        // Check  payment
        paymentApi = apiClient.checkPayment( paymentApi);
        if (paymentApi != null )
            logger.info("the payment: \n" + paymentApi + "\nhas been correctly issued." );
        else
            logger.warn("the payment check failed!!" );
    }

}
