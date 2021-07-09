package br.com.zupacademy.enricco.mercadolivre.config.security;

import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoggedUser implements UserDetails {
    private Long id;
    private String login;
    private String password;

    public LoggedUser(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getHashed_password();
    }

    public Long getId() {
        return id;
    }


    public String getLogin() {
        return login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
