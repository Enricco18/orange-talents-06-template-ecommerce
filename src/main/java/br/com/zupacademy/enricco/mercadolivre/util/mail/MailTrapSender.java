package br.com.zupacademy.enricco.mercadolivre.util.mail;

import br.com.zupacademy.enricco.mercadolivre.config.email.EmailType;
import br.com.zupacademy.enricco.mercadolivre.config.email.MailTrapConfiguration;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.EmailParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Primary
public class MailTrapSender implements MailSender{
    @Autowired
    private MailTrapConfiguration emailConfiguration;

    @Autowired
    private JavaMailSender javaMailSender;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(emailConfiguration.getHost());
        mailSenderImpl.setPort(emailConfiguration.getPort());
        mailSenderImpl.setUsername(emailConfiguration.getUsername());
        mailSenderImpl.setPassword(emailConfiguration.getPassword());
        return mailSenderImpl;
    }

    @Override
    public void sendEmail(EmailType type, EmailParameters parameter ) {
        MimeMessageHelper message ;

        switch (type){
            case QUESTION:
                message = buildQuestionEmail(parameter);
                break;
            default:
                message = null;
        }

        getJavaMailSender().send(message.getMimeMessage());
    }

    private MimeMessageHelper buildQuestionEmail(EmailParameters parameter){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject(parameter.getSubject());
            helper.setFrom(parameter.getFrom());
            helper.setTo(parameter.getTo());

            String mail = parameter.getContentFromTemplate("question-template.flth",parameter.getModel());
            helper.setText(mail, true);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return helper;

    }
}
