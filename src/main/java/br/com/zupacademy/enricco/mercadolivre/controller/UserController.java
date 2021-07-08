package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewUserRequest;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody @Valid NewUserRequest userRequest){
        logger.info("METHOD: POST | PATH: /user | FUNCTION: createUser | BODY: " + userRequest.toString());
        User user = userRequest.toModel();
        entityManager.persist(user);

        return ResponseEntity.ok().build();
    }
}
