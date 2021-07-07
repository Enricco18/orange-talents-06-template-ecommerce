package br.com.zupacademy.enricco.mercadolivre.exception.dto;

public class NotValidArgumentDTO {
    private String field;
    private String message;

    public NotValidArgumentDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
