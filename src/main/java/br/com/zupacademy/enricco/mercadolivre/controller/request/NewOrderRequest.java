package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Order;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import br.com.zupacademy.enricco.mercadolivre.util.payment.OrderStatus;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentGateway;
import br.com.zupacademy.enricco.mercadolivre.validation.EntityExists;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewOrderRequest {
    @NotNull
    private PaymentGateway gateway;
    @NotNull
    @EntityExists(domainClass = Product.class)
    private Long product_id;
    @NotNull
    @Positive
    @Min(1)
    private Integer quantity;

    public Long getProduct_id() {
        return product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    @Override
    public String toString() {
        return "NewOrderRequest{" +
                "gateway=" + gateway +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                '}';
    }

    public Order toModel(Product product, User user) {
        return new Order(product,this.quantity,user, OrderStatus.STARTED,gateway, product.getPrice());
    }
}
