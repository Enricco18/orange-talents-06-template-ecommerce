package br.com.zupacademy.enricco.mercadolivre.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @URL
    private String url;

    @ManyToOne
    private Product product;

    @Deprecated
    private Image() {
    }

    public Image(@URL @NotBlank String url, @NotNull Product product) {
        this.url = url;
        this.product = product;
    }
}