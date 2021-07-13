package br.com.zupacademy.enricco.mercadolivre.controller.response;

import br.com.zupacademy.enricco.mercadolivre.model.Question;

public class QuestionDTO {
    private String title;
    private String user_asking;
    public QuestionDTO(Question question) {
        this.title = question.getTitle();
        this.user_asking = question.getUser_asking();
    }

    public String getTitle() {
        return title;
    }

    public String getUser_asking() {
        return user_asking;
    }
}
