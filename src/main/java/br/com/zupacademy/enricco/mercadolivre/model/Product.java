package br.com.zupacademy.enricco.mercadolivre.model;

import br.com.zupacademy.enricco.mercadolivre.config.security.LoggedUser;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewCharacteristic;
import br.com.zupacademy.enricco.mercadolivre.controller.request.ProductImageRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Valid
    @ManyToOne
    private Category category;
    @NotNull
    @Valid
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    Set<Characteristic> characteristics = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    Set<Image> images = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime created_at;

    @Deprecated
    private Product() {
    }

    public Product(@NotBlank String name,
                   @NotNull @Positive Integer qtd,
                   @NotBlank @Length(max = 1000) String description,
                   @NotNull @Positive BigDecimal price,
                   @NotNull @Valid Category category,
                   @NotNull @Valid User user_id,
                   @NotNull @Valid List<NewCharacteristic> newCharacteristics
    ) {
        this.name = name;
        this.qtd = qtd;
        this.description = description;
        this.price = price;
        this.category = category;
        this.owner = user_id;

        Set<Characteristic> characteristicSet = newCharacteristics.stream().map(
                newCharacteristic -> newCharacteristic.toModel(this)
        ).collect(Collectors.toSet());

        this.characteristics.addAll(characteristicSet);

        Assert.isTrue(this.characteristics.size()>=3, "Tem que ter no mínimo 3 características");
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public boolean hasEditPermission(LoggedUser userDetails) {
        return userDetails.getId() == this.owner.getId();
    }

    public void addImage(Set<String> urlList) {
        Set<Image> newImages = new HashSet<>();

        urlList.stream().forEach(url ->{
            newImages.add(new Image(url,this));
        });

        this.images.addAll(newImages);

    }
}
