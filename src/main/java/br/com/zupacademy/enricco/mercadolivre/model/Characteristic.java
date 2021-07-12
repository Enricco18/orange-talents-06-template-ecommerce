package br.com.zupacademy.enricco.mercadolivre.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty @NotBlank
    private String name;
    @NotNull @NotEmpty @NotBlank
    private String description;

    @Valid @NotNull
    @ManyToOne
    private Product product;

    public Characteristic(String name, String description, Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }
}
