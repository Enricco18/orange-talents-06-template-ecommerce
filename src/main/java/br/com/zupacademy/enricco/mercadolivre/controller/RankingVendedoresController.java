package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.controller.response.RankingDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RankingVendedoresController {
    @PostMapping("/ranking")
    public void addPointToVendor(RankingDTO body){
        return;
    }
}
