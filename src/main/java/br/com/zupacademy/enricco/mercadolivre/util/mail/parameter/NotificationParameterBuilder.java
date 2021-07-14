package br.com.zupacademy.enricco.mercadolivre.util.mail.parameter;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewOrderRequest;
import br.com.zupacademy.enricco.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationParameterBuilder {
    @Value("${mail.principal}")
    private String from;

    @Autowired
    private EmailParameters parameters;

    public EmailParameters build(Product product, NewOrderRequest request){
        String to = product.getOwner().getLogin();
        String subject = "Tem uma nova oferta sobre o produto: " + product.getName();

        Map<String, String> model = new HashMap<>();
        model.put("product_name", product.getName());
        model.put("quantity", request.getQuantity().toString());
        model.put("price", product.getPrice().toString());
        model.put("total", String.valueOf(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));
        this.parameters.setParameters(to,from,subject,model);
        return  this.parameters;
    }

}
