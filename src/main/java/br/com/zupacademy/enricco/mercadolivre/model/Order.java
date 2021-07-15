package br.com.zupacademy.enricco.mercadolivre.model;

import br.com.zupacademy.enricco.mercadolivre.util.payment.OrderStatus;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentGateway;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentStatus;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "ordertransactions")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
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

    @OneToMany(mappedBy = "order",cascade = CascadeType.MERGE)
    private List<Payment> payments= new ArrayList<>();

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


    public String getProduct_Name() {
        return product.getName();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getBuyer() {
        return buyer.getLogin();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public String getOrderLink(UriComponentsBuilder uriComponentsBuilder){
        return this.gateway.generatePaymentLink(this.id,uriComponentsBuilder);
    }
    public static Order getOrThrow404(EntityManager entityManager, UUID order_id) {
        Order order = entityManager.find(Order.class,order_id);

        if(order==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return order;
    }

    public void setCompleteStatus() {
        this.status = OrderStatus.COMPLETED;
    }

    public boolean stillValid() {
        AtomicInteger tries = new AtomicInteger(3);
        AtomicBoolean hasSuccess = new AtomicBoolean(false);
        this.payments.stream().forEach(payment -> {
            if(payment.getStatus()==PaymentStatus.SUCESSO){
                hasSuccess.set(true);
            }
            tries.getAndDecrement();
        });

        return !(tries.get() <=0 || hasSuccess.get());
    }

    public String getOpinionURL(UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.path("/opinion/{id}").buildAndExpand("is",this.product.getId()).toString();
    }

    public Long getBuyer_Id() {
        return this.buyer.getId();
    }

    public UUID getId() {
        return this.id;
    }

    public Long getVendor_Id() {
        return this.product.getVendorId();
    }

    public void addPayment(Payment log) {
        this.payments.add(log);
    }
}
