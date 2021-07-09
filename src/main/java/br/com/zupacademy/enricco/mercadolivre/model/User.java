package br.com.zupacademy.enricco.mercadolivre.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotBlank @NotEmpty
    @Email
    @Column(unique = true)
    private String login;

    @NotNull @NotBlank @NotEmpty
    private String hashed_password;


    @PastOrPresent
    @CreationTimestamp
    private LocalDateTime created_at;

    @Deprecated
    private User() {
    }

    public User(@NotBlank @Email String login, @NotBlank String hashed_password) {
        this.login = login;
        this.hashed_password = hashed_password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getHashed_password() {
        return hashed_password;
    }
}
