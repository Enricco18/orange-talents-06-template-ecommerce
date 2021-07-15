package br.com.zupacademy.enricco.mercadolivre.config.email;

import br.com.zupacademy.enricco.mercadolivre.util.mail.parameter.EmailParameters;
import org.springframework.mail.javamail.MimeMessageHelper;

public enum EmailType {
    QUESTION{
        @Override
        public String getTemplate() {
            return "question-template.flth";
        }
    },
    ORDER_NOTIFICATION {
        @Override
        public String getTemplate() {
            return "order-notification.flth";
        }
    },
    SUCCESS_NOTIFICATION {
        @Override
        public String getTemplate() {
            return "success-payment.flth";
        }
    },
    ERROR_NOTIFICATION {
        @Override
        public String getTemplate() {
            return "error-payment.flth";
        }
    };

    public abstract String getTemplate();
}
