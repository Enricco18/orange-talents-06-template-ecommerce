package br.com.zupacademy.enricco.mercadolivre.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(1) @Max(5)
    private Short stars;
    @NotBlank @NotNull @NotEmpty
    private String title;
    @NotBlank @NotNull @NotEmpty
    @Length(max = 500)
    private String description;
    @NotNull
    @ManyToOne
    private User user;
    @NotNull
    @ManyToOne
    private Product product;

    @Deprecated
    private Opinion() {
    }

    public Opinion(@Min(1) @Max(5) Short stars,
                   @NotBlank String title,
                   @NotBlank String description,
                   @NotNull User user,
                   @NotNull Product product) {
        this.stars = stars;
        this.title = title;
        this.description = description;
        this.user = user;
        this.product = product;
        Assert.isTrue(stars>=1 && stars<=5, "Tem que ser entre 1 e 5");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opinion opinion = (Opinion) o;
        return Objects.equals(stars, opinion.stars) && Objects.equals(title, opinion.title) && Objects.equals(description, opinion.description) && Objects.equals(user, opinion.user) && Objects.equals(product, opinion.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stars, title, description, user, product);
    }

    public Short getStars() {
        return stars;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user.getLogin();
    }
}
