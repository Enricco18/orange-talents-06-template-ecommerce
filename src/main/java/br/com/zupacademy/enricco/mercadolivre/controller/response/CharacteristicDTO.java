package br.com.zupacademy.enricco.mercadolivre.controller.response;

import br.com.zupacademy.enricco.mercadolivre.model.Characteristic;

public class CharacteristicDTO {
    private String name;
    private String description;
    public CharacteristicDTO(Characteristic characteristic) {
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
