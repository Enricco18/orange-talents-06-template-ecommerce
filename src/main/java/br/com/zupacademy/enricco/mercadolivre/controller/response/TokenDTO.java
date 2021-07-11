package br.com.zupacademy.enricco.mercadolivre.controller.response;

public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
