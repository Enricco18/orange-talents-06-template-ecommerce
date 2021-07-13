package br.com.zupacademy.enricco.mercadolivre.controller.response;

import br.com.zupacademy.enricco.mercadolivre.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    private String name;
    private BigDecimal price;
    private Float average_rating;
    private Integer number_of_reviews;
    private Integer qtd;
    private String description;
    private String category_name;
    private String vendor_name;
    private List<String> images = new ArrayList<>();
    private List<QuestionDTO> questions = new ArrayList<>();
    private List<OpinionDTO> opinions = new ArrayList<>();
    private List<CharacteristicDTO> characteristics = new ArrayList<>();

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.qtd = product.getQtd();
        this.description = product.getDescription();

        this.category_name = product.getCategoryName();
        this.vendor_name = product.getVendorName();

        product.getQuestions().stream().forEach(question -> {
            this.questions.add(new QuestionDTO(question));
        });

        product.getImages().stream().forEach(image -> {
            this.images.add(image.getURL());
        });

        product.getCharacteristics().stream().forEach(characteristic -> {
            this.characteristics.add(new CharacteristicDTO(characteristic));
        });

        OpinionManager opinionManager = new OpinionManager();

        opinionManager.getOpinionsData(product.getOpinions());

        this.average_rating = opinionManager.getAverage_rating();
        this.opinions.addAll(opinionManager.getOpinionDTOList());
        this.number_of_reviews = opinionManager.getNumber_of_ratings();
    }

    public Integer getNumber_of_reviews() {
        return number_of_reviews;
    }

    public List<String> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Float getAverage_rating() {
        return average_rating;
    }

    public Integer getQtd() {
        return qtd;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public List<OpinionDTO> getOpinions() {
        return opinions;
    }

    public List<CharacteristicDTO> getCharacteristics() {
        return characteristics;
    }
}
