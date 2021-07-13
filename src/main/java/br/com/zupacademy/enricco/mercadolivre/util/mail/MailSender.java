package br.com.zupacademy.enricco.mercadolivre.util.mail;

import br.com.zupacademy.enricco.mercadolivre.config.email.EmailType;
import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.EmailParameters;
import org.springframework.mail.javamail.JavaMailSender;

import javax.validation.constraints.Email;

public interface MailSender {
    public JavaMailSender getJavaMailSender();
    public void sendEmail(EmailType email, EmailParameters parameters);
}
