package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Category;
import br.com.zupacademy.enricco.mercadolivre.repository.CategoryRepository;
import br.com.zupacademy.enricco.mercadolivre.validation.EntityExists;
import br.com.zupacademy.enricco.mercadolivre.validation.UniqueValue;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class NewCategoryRequest {
    @NotNull @NotEmpty @NotBlank
    @UniqueValue(domainClass = Category.class,fieldName = "name")
    private String name;

    @EntityExists(domainClass = Category.class)
    private Long parent_category_id;

    public NewCategoryRequest(String name, Long parent_category_id) {
        this.name = name;
        this.parent_category_id = parent_category_id;
    }

    public String getName() {
        return name;
    }

    public Long getParent_category_id() {
        return parent_category_id;
    }

    public Category toModel(CategoryRepository categoryRepository) {
        Category parentCategory = null;
        if(this.parent_category_id != null){
            parentCategory = categoryRepository.findById(this.parent_category_id).orElse(null);
        }
        return new Category(this.name,parentCategory);
    }

    @Override
    public String toString() {
        return "NewCategoryRequest{" +
                "name='" + name + '\'' +
                ", parent_category_id=" + parent_category_id +
                '}';
    }
}
