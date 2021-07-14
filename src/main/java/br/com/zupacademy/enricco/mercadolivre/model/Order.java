package br.com.zupacademy.enricco.mercadolivre.model;

import br.com.zupacademy.enricco.mercadolivre.util.payment.OrderStatus;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentGateway;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_transactions")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @Positive
    private Integer quantity;

    @ManyToOne
    private User buyer;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private PaymentGateway gateway;

    @NotNull
    @Positive
    private BigDecimal price;

    public Order() {
    }

    public Order(Product product, Integer quantity, User buyer, OrderStatus status, PaymentGateway gateway, BigDecimal price) {
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.status = status;
        this.gateway = gateway;
        this.price = price;
    }

    public String getOrderLink(UriComponentsBuilder uriComponentsBuilder){
        return this.gateway.generatePaymentLink(this.id,uriComponentsBuilder);
    }
}
