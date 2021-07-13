package br.com.zupacademy.enricco.mercadolivre.config.email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public interface EmailConfiguration {
    public String getHost();

    public Integer getPort();

    public String getUsername();

    public String getPassword();

}
