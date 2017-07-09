package io.apiary.client.dao;

/**
 * Created by Salvatore Rapisarda
 *
 */
public class PaymentApi {
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public  String toString(){
        return payment.toString();
    }

}
