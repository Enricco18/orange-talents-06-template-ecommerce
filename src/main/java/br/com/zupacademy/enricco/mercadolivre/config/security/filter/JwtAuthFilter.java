package br.com.zupacademy.enricco.mercadolivre.config.security.filter;

import br.com.zupacademy.enricco.mercadolivre.config.security.AuthenticationService;
import br.com.zupacademy.enricco.mercadolivre.config.security.TokenManager;
import br.com.zupacademy.enricco.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthFilter extends OncePerRequestFilter {
    private UserRepository userRepository;
    private AuthenticationService authenticationService;
    private TokenManager tokenManager;

    public JwtAuthFilter(UserRepository userRepository, AuthenticationService authenticationService, TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtToken(httpServletRequest);

        if(token!=null&&tokenManager.validateToken(token)){
            UserDetails userDetails = authenticationService.loadUserByUsername(tokenManager.getLogin(token));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());


            SecurityContextHolder.getContext().setAuthentication(auth);
        }


        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    private String getJwtToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");

        if(token!= null){
            token = token.split(" ")[1];
        }

        return token;
    }
}
