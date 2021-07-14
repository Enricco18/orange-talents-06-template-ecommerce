package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.config.email.EmailType;
import br.com.zupacademy.enricco.mercadolivre.config.security.LoggedUser;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewOrderRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Order;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import br.com.zupacademy.enricco.mercadolivre.util.mail.MailSender;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.EmailParameters;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.NotificationParameterBuilder;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.QuestionParameterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private MailSender sender;

    @Autowired
    private NotificationParameterBuilder parameterBuilder;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody @Valid NewOrderRequest request,
                                         Authentication authentication,
                                         UriComponentsBuilder uriComponentsBuilder){
        logger.info("METHOD: POST | PATH: /order| FUNCTION: createOrder | BODY: " + request.toString());
        Product product = entityManager.find(Product.class,request.getProduct_id());
        LoggedUser userDetails = (LoggedUser) authentication.getPrincipal();
        User user = User.getOrThrow404(entityManager,userDetails.getId());

        if (product.hasOwnership(userDetails)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não pode comprar o seu próprio produto");
        }

        if(!product.reduceStock(request.getQuantity())){
            return ResponseEntity.badRequest().body("Estoque insuficiente para compra!");
        }



        Order order = request.toModel(product,user);
        entityManager.persist(order);

        EmailParameters parameters = parameterBuilder.build(product,request);
        sender.sendEmail(EmailType.ORDER_NOTIFICATION,parameters);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", order.getOrderLink(uriComponentsBuilder));

        return  ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
