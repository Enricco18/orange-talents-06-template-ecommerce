package br.com.zupacademy.enricco.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EntityExistsValidator implements ConstraintValidator<EntityExists,Object> {
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o==null){
            return true;
        }
        Query query = entityManager.createQuery("SELECT 1 FROM "+domainClass.getName()+ " WHERE id=:value");
        query.setParameter("value",o);
        List<?> results = query.getResultList();


        return !results.isEmpty();
    }

    @Override
    public void initialize(EntityExists constraintAnnotation) {
       domainClass = constraintAnnotation.domainClass();
    }
}
