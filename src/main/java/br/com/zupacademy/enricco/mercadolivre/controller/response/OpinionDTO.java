package br.com.zupacademy.enricco.mercadolivre.controller.response;

import br.com.zupacademy.enricco.mercadolivre.model.Opinion;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

public class OpinionDTO {
    private Short stars;
    private String title;
    private String description;
    private String user_name;
    public OpinionDTO(Opinion opinion) {
        this.stars = opinion.getStars();
        this.title = opinion.getTitle();
        this.description = opinion.getDescription();
        this.user_name = opinion.getUser();
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

    public String getUser_name() {
        return user_name;
    }
}
