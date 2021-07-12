package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Category;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import br.com.zupacademy.enricco.mercadolivre.validation.EntityExists;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewProductRequest {
    @NotNull @NotEmpty @NotBlank
    private String name;
    @NotNull
    @Positive
    private Integer qtd;
    @NotNull @NotEmpty @NotBlank
    @Length(max = 1000)
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @EntityExists(domainClass = Category.class)
    private Long category_id;
    @Size(min=3)
    @Valid
    @NotNull
    private List<NewCharacteristic> characteristic_list;

    public Product toModel(EntityManager manager, Long owner_id){
        Category category = manager.find(Category.class, category_id);
        User user = manager.find(User.class, owner_id);
        Assert.notNull(user,"Usuário não pode ser nulo");
        Assert.isTrue(characteristic_list.size()>=3,"Tamanho tem que ser maior que 3");

        Product product = new Product(this.name,
                this.qtd,
                this.description,
                this.price,
                category,
                user,
                characteristic_list);

        return  product;
    }

    public String getName() {
        return name;
    }

    public Integer getQtd() {
        return qtd;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getCategory_id() {
        return category_id;
    }


    public List<NewCharacteristic> getCharacteristic_list() {
        return characteristic_list;
    }

    public Set<NewCharacteristic> repeatedCategory() {
        Set<NewCharacteristic> repeated = new HashSet<>();
        Set<NewCharacteristic> exists = new HashSet<>();

        this.characteristic_list.stream().forEach(characteristic -> {
            if(!exists.add(characteristic)){
                repeated.add(characteristic);
            }
        });

        return repeated;
    }

    @Override
    public String toString() {
        return "NewProductRequest{" +
                "name='" + name + '\'' +
                ", qtd=" + qtd +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category_id=" + category_id +
                ", characteristic_list=" + characteristic_list +
                '}';
    }
}
