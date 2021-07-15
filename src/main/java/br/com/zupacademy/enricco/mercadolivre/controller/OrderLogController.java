package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.config.email.EmailType;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewPaymentRequest;
import br.com.zupacademy.enricco.mercadolivre.controller.response.NotaFiscalDTO;
import br.com.zupacademy.enricco.mercadolivre.controller.response.RankingDTO;
import br.com.zupacademy.enricco.mercadolivre.model.Payment;
import br.com.zupacademy.enricco.mercadolivre.model.Order;
import br.com.zupacademy.enricco.mercadolivre.util.mail.MailSender;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.EmailParameters;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.GeneralParameterBuilder;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class OrderLogController {
    @PersistenceContext
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(OrderLogController.class);

    @Autowired
    private MailSender sender;

    @Autowired
    private GeneralParameterBuilder generalParameterBuilder;

    @Autowired
    private NotaFiscalController notaFiscalController;

    @Autowired
    private RankingVendedoresController rankingController;

    @PostMapping("/order/{id}")
    @Transactional
    public ResponseEntity<?> handleGatewayResponse(@PathVariable("id") UUID order_id,
                                                   @RequestBody @Valid NewPaymentRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder){
        logger.info("METHOD: POST | PATH: /order/{id} | FUNCTION: handleGatewayResponse | BODY: " + request.toString());
        Order order = Order.getOrThrow404(entityManager,order_id);

        if(!order.stillValid()){
            return  ResponseEntity.badRequest().body("Ordem de compra não é mais válida");
        }

        Payment log = request.toModel(order);

        if(log.getStatus()== PaymentStatus.SUCESSO){
            order.setCompleteStatus();

            notaFiscalController.generateNF(new NotaFiscalDTO(order.getBuyer_Id(),order.getId()));
            rankingController.addPointToVendor(new RankingDTO(order.getVendor_Id(),order.getId()));

            EmailParameters parameters = generalParameterBuilder.build(log.getStatus(),order,uriComponentsBuilder);
            sender.sendEmail(EmailType.SUCCESS_NOTIFICATION,parameters);

        }else {

            EmailParameters parameters = generalParameterBuilder.build(log.getStatus(),order,uriComponentsBuilder);
            sender.sendEmail(EmailType.ERROR_NOTIFICATION,parameters);
        }


        order.addPayment(log);

        entityManager.merge(order);

        return ResponseEntity.ok().build();
    }
}
