package br.com.zupacademy.enricco.mercadolivre.util.mail.parameter;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuestionParameterBuilder {
    @Value("${mail.principal}")
    private String from;


    @Autowired
    private EmailParameters parameters;

    public void build(Product product, NewQuestionRequest request){
        String to = product.getOwner().getLogin();
        String subject = "Tem uma nova pergunta sobre o produto: " + product.getName();

        Map<String,String> model = new HashMap<>();
        model.put("question", request.getTitle());
        parameters.setParameters(to,from,subject,model);
    }

    public EmailParameters getParameters() {
        return parameters;
    }
}
