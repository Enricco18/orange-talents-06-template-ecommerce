package br.com.zupacademy.enricco.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {EntityExistsValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface EntityExists {
    String message() default "Esse ID deve pertencer há uma entidade no banco de dados!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domainClass();
}
