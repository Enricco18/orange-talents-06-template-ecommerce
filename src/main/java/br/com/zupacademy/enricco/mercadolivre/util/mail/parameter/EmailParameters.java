package br.com.zupacademy.enricco.mercadolivre.util.mail.parameter;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.util.Map;

@Component
public class EmailParameters {
    @Qualifier("getFreeMarkerConfiguration")
    @Autowired
    Configuration fmConfiguration;

    private String to;
    private String from;
    private String subject;
    private Map< String, ? > model;

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setParameters(String to,String from,String subject,Map<String, ?> model){
        this.to=to;
        this.from = from;
        this.subject=subject;
        this.model = model;
    }

    public String getContentFromTemplate(String url , Map< String, ? > model)     {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate(url), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
