package br.com.zupacademy.enricco.mercadolivre.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(url, image.url) && Objects.equals(product, image.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, product);
    }
}
