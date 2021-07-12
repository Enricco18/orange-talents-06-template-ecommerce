package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.config.security.LoggedUser;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewOpinionRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OpinionController {
    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(OpinionController.class);

    @PostMapping("/product/{id}/opinion")
    @Transactional
    public ResponseEntity<?> addOpinion(@PathVariable("id") Long product_id,
                                        @RequestBody @Valid NewOpinionRequest opinionRequest,
                                        Authentication authentication){

        logger.info("METHOD: POST | PATH: /product/id/opinion | FUNCTION: addOpinion | BODY: " + opinionRequest.toString());

        Assert.isInstanceOf(LoggedUser.class,authentication.getPrincipal(),"Não é do tipo LoggedUser");
        LoggedUser userDetails = (LoggedUser) authentication.getPrincipal();
        Assert.notNull(userDetails,"Não pode não ter usuário logado!");

        User user = entityManager.find(User.class,userDetails.getId());
        Product product = entityManager.find(Product.class,product_id);

        if(product==null||user==null){
            return ResponseEntity.notFound().build();
        }

        //Vc não pode opinar sobre o próprio produto
        if (product.hasOwnership(userDetails)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        product.addOpinion(opinionRequest,user);

        entityManager.merge(product);

        return ResponseEntity.ok().build();
    }
}
