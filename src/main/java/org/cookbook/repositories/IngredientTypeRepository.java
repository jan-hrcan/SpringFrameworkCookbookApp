package org.cookbook.repositories;

import org.cookbook.models.IngredientType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientTypeRepository extends CrudRepository<IngredientType, Long> {

}
