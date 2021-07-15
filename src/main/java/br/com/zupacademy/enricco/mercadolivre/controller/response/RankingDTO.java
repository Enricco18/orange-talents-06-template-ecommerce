package br.com.zupacademy.enricco.mercadolivre.controller.response;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RankingDTO {
    private Long id_vendor;
    private UUID id_compra;

    public RankingDTO(@NotNull Long id_user, @NotNull UUID id_compra) {
        this.id_vendor = id_user;
        this.id_compra = id_compra;
    }

    public Long getId_vendor() {
        return id_vendor;
    }

    public UUID getId_compra() {
        return id_compra;
    }
}
