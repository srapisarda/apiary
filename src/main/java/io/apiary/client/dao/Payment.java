package io.apiary.client.dao;

/**
 * Created by Salvatore Rapisarda
 *
 *
 */
public class Payment {
    private String id;
    private double amount;
    private String currency;
    private String recipient_id;
    private String status;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return  (id != null ? "id: " + id + ", ": "") +
                "recipient_id:" +  recipient_id +
                ", amount " + amount +
                (status != null ? ", status  " + status: "");
    }
}
