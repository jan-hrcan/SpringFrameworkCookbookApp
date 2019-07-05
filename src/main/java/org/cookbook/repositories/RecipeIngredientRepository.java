package org.cookbook.repositories;

import org.cookbook.models.Ingredient;
import org.cookbook.models.Recipe;
import org.cookbook.models.RecipeIngredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, Long>{

	// Query by recipe
	Iterable<RecipeIngredient> findByRecipe (Recipe recipe);
	
	// Query by ingredient
	Iterable<RecipeIngredient> findByIngredient (Ingredient ingredient);

	// Query by both recipe and ingredient
	Iterable<RecipeIngredient> findByRecipeAndIngredient (Recipe recipe, Ingredient ingredient);

	// Deleting ALL by Recipe
	@Transactional
	void deleteAllByRecipe (Recipe recipe);

	// Deletion by Ingredient
	void deleteByIngredient (Ingredient ingredient);
}
