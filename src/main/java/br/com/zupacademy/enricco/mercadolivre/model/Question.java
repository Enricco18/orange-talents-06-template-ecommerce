package br.com.zupacademy.enricco.mercadolivre.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @NotNull @NotEmpty
    private String title;
    @ManyToOne
    @NotNull
    private User user_asking;
    @ManyToOne
    @NotNull
    private Product product;
    @CreationTimestamp
    private LocalDateTime created_at;

    @Deprecated
    private Question() {
    }

    public Question(@NotBlank String title,
                    @NotNull User user_asking,
                    @NotNull Product product) {
        this.title = title;
        this.user_asking = user_asking;
        this.product = product;
    }
}
