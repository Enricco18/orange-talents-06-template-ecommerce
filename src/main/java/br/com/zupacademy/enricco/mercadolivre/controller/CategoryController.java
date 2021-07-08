package br.com.zupacademy.enricco.mercadolivre.controller;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewCategoryRequest;
import br.com.zupacademy.enricco.mercadolivre.model.Category;
import br.com.zupacademy.enricco.mercadolivre.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody @Valid NewCategoryRequest categoryRequest){
        logger.info("METHOD: POST | PATH: /category | FUNCTION: createCategory | BODY: " + categoryRequest.toString());
        Category category = categoryRequest.toModel(categoryRepository);

        categoryRepository.save(category);
        return ResponseEntity.ok().build();
    }
}
