package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Opinion;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class NewOpinionRequest {
    @Min(1) @Max(5)
    private Short stars;
    @NotBlank @NotNull @NotEmpty
    private String title;
    @NotBlank @NotNull @NotEmpty
    @Length(max = 500)
    private String description;

    public Short getStars() {
        return stars;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Opinion toModel(Product product, User user) {
        return new Opinion(this.stars,this.title,this.description,user,product);
    }
}
