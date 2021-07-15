package br.com.zupacademy.enricco.mercadolivre.controller.response;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class NotaFiscalDTO {
    private Long id_user;
    private UUID id_compra;

    public NotaFiscalDTO(Long id_user, @NotNull UUID id_compra) {
        this.id_user = id_user;
        this.id_compra = id_compra;
    }

    public Long getId_user() {
        return id_user;
    }

    public UUID getId_compra() {
        return id_compra;
    }
}
