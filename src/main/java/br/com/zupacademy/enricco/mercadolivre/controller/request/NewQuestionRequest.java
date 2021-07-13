package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.Question;
import br.com.zupacademy.enricco.mercadolivre.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NewQuestionRequest {
    @NotNull @NotEmpty @NotBlank
    public String title;


    @Override
    public String toString() {
        return "NewQuestionRequest{" +
                "title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public Question toModel(Product product, User user) {
        return new Question(this.title,user,product);
    }
}
