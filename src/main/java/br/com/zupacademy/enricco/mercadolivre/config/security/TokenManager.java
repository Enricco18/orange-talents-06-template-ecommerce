package br.com.zupacademy.enricco.mercadolivre.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenManager {
    private Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${json.exp}")
    private Long exp;

    public String generateToken(Authentication auth){
        LoggedUser user =  (LoggedUser) auth.getPrincipal();
        Date today = new Date();
        Date expDate = new Date(today.getTime()+exp);

        return Jwts.builder()
                .setIssuer("Zup")
                .setIssuedAt(today)
                .setSubject(user.getLogin())
                .setExpiration(expDate)
                .signWith(secret)
                .compact();

    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return  true;
        }catch (Exception E){
            return false;
        }

    }

    public String getLogin(String token){
        return  Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }
}
