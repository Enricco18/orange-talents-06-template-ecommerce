package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.config.security.TokenManager;
import br.com.zupacademy.enricco.mercadolivre.controller.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody @Valid LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginRequest.getLogin(),loginRequest.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(userToken);
            String token = tokenManager.generateToken(auth);
            return ResponseEntity.ok(token);

        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
