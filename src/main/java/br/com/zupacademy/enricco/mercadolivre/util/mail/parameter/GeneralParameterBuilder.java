package br.com.zupacademy.enricco.mercadolivre.util.mail.parameter;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Order;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.util.payment.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class GeneralParameterBuilder {
    @Value("${mail.principal}")
    private String from;


    @Autowired
    private EmailParameters parameters;

    public EmailParameters build(PaymentStatus status, Order order, UriComponentsBuilder uriComponentsBuilder){
        if(status == PaymentStatus.SUCESSO ){
            buildSucess(order,uriComponentsBuilder);
            return this.parameters;
        }
        buildError(order,uriComponentsBuilder);
        return this.parameters;
    }

    private void buildError(Order order, UriComponentsBuilder uriComponentsBuilder) {
        String subject = "Pagamento recusado para o produto \"" + order.getProduct_Name() +"\"";

        Map<String,String> model = new HashMap<>();
        model.put("url", order.getOrderLink(uriComponentsBuilder));
        this.parameters.setParameters(order.getBuyer(),from,subject,model);
    }

    private void buildSucess(Order order, UriComponentsBuilder uriComponentsBuilder) {
        String subject = "Parabéns! A compra do produto \"" + order.getProduct_Name() +"\" foi concluída com sucesso!";

        Map<String,String> model = new HashMap<>();
        model.put("product_name", order.getProduct_Name());
        model.put("gateway", order.getGateway().name());
        model.put("number", order.getQuantity().toString());
        model.put("price", order.getPrice().toString());
        model.put("url_opinion", order.getOpinionURL(uriComponentsBuilder));
        model.put("total", order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())).toString());





        this.parameters.setParameters(order.getBuyer(),from,subject,model);
    }

    public EmailParameters getParameters() {
        return parameters;
    }
}
