package br.com.zupacademy.enricco.mercadolivre.model;

import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    private Order order;

    @NotNull
    @Type(type = "uuid-char")
    private UUID id_gateway;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private PaymentStatus status;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Deprecated
    private Payment() {
    }

    public Payment(Order order, UUID id_gateway, PaymentStatus status) {
        this.order = order;
        this.id_gateway = id_gateway;
        this.status = status;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LogOrder{" +
                "id=" + id +
                ", order=" + order +
                ", id_gateway=" + id_gateway +
                ", status=" + status +
                ", created_at=" + created_at +
                '}';
    }
}
