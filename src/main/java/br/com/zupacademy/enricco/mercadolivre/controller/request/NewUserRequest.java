package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.User;
import br.com.zupacademy.enricco.mercadolivre.validation.UniqueValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

public class NewUserRequest {
    @NotNull @NotBlank @NotEmpty
    @Email
    @UniqueValue(domainClass = User.class, fieldName = "login")
    private String login;

    @NotNull @NotBlank @NotEmpty
    @Length(min = 6)
    private String password;

    public User toModel(){
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        String hashed_password = crypt.encode(getPassword());
        return new User(login,hashed_password);
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
