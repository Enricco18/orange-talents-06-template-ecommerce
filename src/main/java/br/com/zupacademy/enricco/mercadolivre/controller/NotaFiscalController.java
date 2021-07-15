package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.controller.response.NotaFiscalDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NotaFiscalController {
    @PostMapping("/nf")
    public void generateNF(NotaFiscalDTO body){
        return;
    }
}
