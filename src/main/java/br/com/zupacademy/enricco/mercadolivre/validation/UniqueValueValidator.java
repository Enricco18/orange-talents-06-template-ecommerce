package br.com.zupacademy.enricco.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class UniqueValueValidator implements ConstraintValidator<UniqueValue,Object> {
    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> domainName;
    private String fieldName;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.domainName = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Query query = entityManager.createQuery("SELECT 1 FROM " + domainName.getName()+" x WHERE x."+fieldName+"=:value");
        query.setParameter("value",o.toString());
        List<?> list = query.getResultList();
        return list.isEmpty();
    }
}
