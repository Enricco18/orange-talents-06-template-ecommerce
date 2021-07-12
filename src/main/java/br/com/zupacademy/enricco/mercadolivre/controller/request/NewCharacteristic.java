package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Characteristic;
import br.com.zupacademy.enricco.mercadolivre.model.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class NewCharacteristic {
    @NotNull @NotEmpty @NotBlank
    private String name;
    @NotNull @NotEmpty @NotBlank
    private String description;

    public NewCharacteristic(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public Characteristic toModel(Product product){
        return new Characteristic(this.name,this.description,product);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewCharacteristic that = (NewCharacteristic) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
