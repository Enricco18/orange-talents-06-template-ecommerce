package br.com.zupacademy.enricco.mercadolivre.config.security;

import br.com.zupacademy.enricco.mercadolivre.config.security.LoggedUser;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import br.com.zupacademy.enricco.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(s);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return new LoggedUser(user.get());
    }
}
