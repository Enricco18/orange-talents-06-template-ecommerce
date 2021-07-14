package br.com.zupacademy.enricco.mercadolivre.util.payment;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

public enum PaymentGateway {
    pagseguro{
        @Override
        public String generatePaymentLink(UUID id,UriComponentsBuilder uriComponentsBuilder){
            String returnUrl = uriComponentsBuilder.path("/pagseguro-return/{id}").buildAndExpand(id.toString()).toString();
            return "https://www.pagseguro.com.br?returnId="+id.toString()+"&redirectUrl="+returnUrl;
        }
    },
    paypal{
        @Override
        public String generatePaymentLink(UUID id,UriComponentsBuilder uriComponentsBuilder) {
            String returnUrl = uriComponentsBuilder.path("/paypal-return/{id}").buildAndExpand(id.toString()).toString();
            return "https://www.paypal.com?buyerId="+id.toString()+"&redirectUrl="+returnUrl;
        }
    };

    public abstract String generatePaymentLink(UUID id,UriComponentsBuilder uriComponentsBuilder);

}
