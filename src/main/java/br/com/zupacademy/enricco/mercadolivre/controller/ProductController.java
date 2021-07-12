package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.config.security.LoggedUser;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewProductRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.validation.UniqueCategoryName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(new UniqueCategoryName());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createProduct(@RequestBody @Valid NewProductRequest request, Authentication authentication){
        logger.info("METHOD: POST | PATH: /product | FUNCTION: createProduct | BODY: " + request.toString());
        Assert.isInstanceOf(LoggedUser.class,authentication.getPrincipal(),"Não é do tipo LoggedUser");

        LoggedUser userDetails = (LoggedUser) authentication.getPrincipal();

        Assert.notNull(userDetails,"Não pode não ter usuário logado!");
        Product product = request.toModel(entityManager, userDetails.getId());

        entityManager.persist(product);
        product.getCharacteristics().stream().forEach(characteristic -> entityManager.persist(characteristic));
        return ResponseEntity.ok().build();
    }
}
