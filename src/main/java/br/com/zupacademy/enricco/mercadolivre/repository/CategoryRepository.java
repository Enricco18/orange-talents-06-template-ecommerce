package br.com.zupacademy.enricco.mercadolivre.repository;

import br.com.zupacademy.enricco.mercadolivre.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
