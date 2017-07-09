package io.apiary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.apiary.client.dao.*;
import io.apiary.client.exceptions.ApiaryException;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salvatore Rapisarda
 *
 *
 */
class ApiClient {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper mapper = new ObjectMapper();
    private final String endPoint;

    /**
     * {@link ApiClient} Constructor method
     * This client is based on http://docs.coolpayapi.apiary.io/#reference
     * @param endPoint String represented the endpoint
     */
    ApiClient(String endPoint){
        this.endPoint= endPoint;
    }


    private CloseableHttpClient client = HttpClientBuilder.create().build();


    /**
     * Authenticate to the API service
     * @param user {@link User} to authenticate
     * @return a {@link TokenApi} which contain the
     * @throws Exception as instance of {@link ApiaryException}
     */
    TokenApi authenticate(User user) throws Exception {
        URI  uri = new URI( endPoint + "/api/login");
        HttpPost request = new HttpPost(uri);
        StringEntity post = new StringEntity( getJson(user));
        post.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        request.setEntity(post);
        //
        HttpResponse response = client.execute(request);
        String contentPayload = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() != 200 ) {
            Exception e =  createExceptionFromStatus(response.getStatusLine(), contentPayload, "authentication");
            logger.error("An error occurred during the authentication!!! " ,e);
            throw e;
        }
        return  mapper.readValue(contentPayload, TokenApi.class);
    }

    /**
     * Add a {@link io.apiary.client.dao.Recipient} to the service api
     * @param token a string represented the token key
     * @param name a string represented the name
     * @return a {@link RecipientApi}
     * @throws Exception as instance of {@link ApiaryException}
     */
    RecipientApi addRecipient(String token, String name ) throws Exception {
        URI  uri = new URI( endPoint +"/api/recipients"   );
        logger.info(uri.toString());
        HttpPost request = new HttpPost(uri);

        StringEntity postEntity =  new StringEntity(getJson(new RecipientApi(new Recipient(name))) );
        addContentHeader(postEntity, token);
        request.setEntity(postEntity);

        HttpResponse response = client.execute(request);
        String contentPayload = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() != 201 ) {
            Exception e =  createExceptionFromStatus(response.getStatusLine(), contentPayload, "adding a recipient");
            logger.error("An error occurred during  adding a recipient!!! " ,e);
            throw e;
        }
        return   mapper.readValue(contentPayload, RecipientApi.class);

    }

    /**
     * Create a payment for a {@link io.apiary.client.dao.Recipient}
     * @param token a string represented the token key
     * @param paymentApi as instance of {@link PaymentApi}
     * @return an instance of  {@link PaymentApi}
     * @throws Exception as instance of {@link ApiaryException}
     */
    PaymentApi createPayment(String token, PaymentApi paymentApi) throws Exception{
        URI  uri = new URI( endPoint +"/api/payments" );
        HttpPost request = new HttpPost(uri);

        StringEntity postEntity = new StringEntity( getJson(paymentApi));
        addContentHeader(postEntity, token);

        HttpResponse response = client.execute(request);
        String contentPayload = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() != 201 ) {
            Exception e =  createExceptionFromStatus(response.getStatusLine(), contentPayload, "creating payment" );
            logger.error("An error occurred during  creating a payment!!! " ,e);
            throw e;
        }
        return mapper.readValue(contentPayload, PaymentApi.class);

    }

    /**
     * Check that a payment has been issue is processing.
     *
     * @param paymentApi {@link PaymentApi}
     * @return a {@link PaymentApi}
     * @throws Exception as instance of {@link ApiaryException}
     */
    PaymentApi checkPayment(PaymentApi paymentApi) throws Exception{
        URI  uri = new URI( endPoint +"/api/payments" );
        HttpPost request = new HttpPost(uri);

        StringEntity postEntity = new StringEntity( getJson(paymentApi));
        addContentHeader(postEntity, null);

        HttpResponse response = client.execute(request);
        String contentPayload = EntityUtils.toString(response.getEntity());
        if ( response.getStatusLine().getStatusCode() != 201) {
            if (response.getStatusLine().getStatusCode() == 422  )
                return  null;

            Exception e =  createExceptionFromStatus(response.getStatusLine(), contentPayload, "checking payment");
            logger.error("An error occurred during  creating a payment!!! " ,e);
            throw e;

        }
        return mapper.readValue(contentPayload, PaymentApi.class);
    }

    private String  getJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    private void addContentHeader(AbstractHttpEntity entity, String token){
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        if ( token != null)
            entity.setContentType(new BasicHeader("Authorization", "Bearer " + token + "") );
    }

    private Exception createExceptionFromStatus(StatusLine status, String payload, String phase){
        String msg = " status: " + status.getStatusCode() +
                " - reason: " + status.getReasonPhrase() +
                " - payload: " + payload;
        ApiaryException e =  new ApiaryException(msg);
        logger.error("An error occurred during the " + phase + "!!! " ,e);
        return  e;
    }

}
