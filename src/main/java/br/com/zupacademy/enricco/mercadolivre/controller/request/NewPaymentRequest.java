package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Payment;
import br.com.zupacademy.enricco.mercadolivre.model.Order;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentStatus;

import java.util.UUID;

public class NewPaymentRequest {
    private UUID id_gateway;
    private PaymentStatus status;

    public UUID getId_gateway() {
        return id_gateway;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "NewPaymentRequest{" +
                "id_gateway=" + id_gateway +
                ", status=" + status +
                '}';
    }

    public Payment toModel(Order order) {
        return new Payment(order,this.id_gateway,this.status);
    }
}
