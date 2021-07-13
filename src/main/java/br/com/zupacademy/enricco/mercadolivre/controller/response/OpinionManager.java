package br.com.zupacademy.enricco.mercadolivre.controller.response;

import br.com.zupacademy.enricco.mercadolivre.model.Opinion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OpinionManager {
    private Float average_rating;
    private Integer totalOfStars = 0;
    private Integer number_of_ratings = 0;
    private List<OpinionDTO> opinionDTOList= new ArrayList<>();

    public void getOpinionsData(List<Opinion> opinions){
        opinions.stream().forEach(opinion -> {
            opinionDTOList.add(new OpinionDTO(opinion));
            number_of_ratings++;
            totalOfStars += opinion.getStars();
            average_rating = (float) totalOfStars/number_of_ratings;
        });
    }

    public Float getAverage_rating() {
        return average_rating;
    }

    public Integer getNumber_of_ratings() {
        return number_of_ratings;
    }

    public List<OpinionDTO> getOpinionDTOList() {
        return opinionDTOList;
    }
}
