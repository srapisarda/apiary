package io.apiary.client.dao;

/**
 * Created by
 *      Salvatore Rapisarda
 */
public class RecipientApi {
    public RecipientApi(){}

    public RecipientApi(Recipient recipient) {
        this.recipient= recipient;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    private Recipient recipient;
}
