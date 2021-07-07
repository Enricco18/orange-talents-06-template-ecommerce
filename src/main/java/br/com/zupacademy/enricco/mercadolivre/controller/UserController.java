package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewUserRequest;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody @Valid NewUserRequest userRequest){
        User user = userRequest.toModel();
        entityManager.persist(user);

        return ResponseEntity.ok().build();
    }
}
