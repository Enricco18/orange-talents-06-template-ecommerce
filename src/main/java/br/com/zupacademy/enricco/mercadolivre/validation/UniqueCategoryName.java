package br.com.zupacademy.enricco.mercadolivre.validation;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UniqueCategoryName implements Validator {
    private Logger logger = LoggerFactory.getLogger(UniqueCategoryName.class);
    @Override
    public boolean supports(Class<?> aClass) {
        return NewProductRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(errors.hasErrors()){
            return;
        }

        NewProductRequest productRequest = (NewProductRequest) o;

        var repeatedList= productRequest.repeatedCategory();
        if(!repeatedList.isEmpty()){
            logger.error("Insert of Products rejected: Duplicated value - " + repeatedList.toString());
            errors.rejectValue("characteristic_list",null,"Características duplicadas com os nomes:"+ repeatedList.toString() );
        }
    }
}
