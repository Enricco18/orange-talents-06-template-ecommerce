package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

public class NewUserRequest {
    @NotNull @NotBlank @NotEmpty
    @Email
    private String login;

    @NotNull @NotBlank @NotEmpty
    @Length(min = 6)
    private String password;

    public User toModel(){
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        String hashed_password = crypt.encode(getPassword());
        return new User(login,hashed_password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
