package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.config.email.EmailType;
import br.com.zupacademy.enricco.mercadolivre.config.security.LoggedUser;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewOpinionRequest;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import br.com.zupacademy.enricco.mercadolivre.util.mail.MailSender;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.EmailParameters;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.QuestionParameterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.validation.constraints.Email;

@RestController
public class QuestionController {
    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionParameterBuilder parameterBuilder;

    @Autowired
    private MailSender sender;

    @PostMapping("/product/{id}/question")
    @Transactional
    public ResponseEntity<?> addQuestion(@PathVariable("id") Long product_id,
                                        @RequestBody @Valid NewQuestionRequest request,
                                        Authentication authentication){

        logger.info("METHOD: POST | PATH: /product/id/question | FUNCTION: addQuestion | BODY: " + request.toString());
        LoggedUser userDetails = (LoggedUser) authentication.getPrincipal();

        User user = User.getOrThrow404(entityManager,userDetails.getId());
        Product product = Product.getOrThrow404(entityManager,product_id);

        //Vc não pode perguntar sobre o próprio produto
        if (product.hasOwnership(userDetails)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        product.addQuestion(request,user);

        entityManager.merge(product);
        parameterBuilder.build(product,request);

        sender.sendEmail(EmailType.QUESTION,parameterBuilder.getParameters());

        return ResponseEntity.ok().build();
    }
}
