package br.com.zupacademy.enricco.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToOne
    private Category parent_category;

    @Deprecated
    private Category() {
    }

    public Category(@NotBlank String name, Category parent_category) {
        this.name = name;
        this.parent_category = parent_category;
    }

    public String getName() {
        return this.name;
    }
}
