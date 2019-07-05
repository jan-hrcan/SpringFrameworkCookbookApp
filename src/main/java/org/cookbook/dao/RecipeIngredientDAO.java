package org.cookbook.dao;

import org.cookbook.models.Ingredient;
import org.cookbook.models.Recipe;
import org.cookbook.models.RecipeIngredient;
import org.cookbook.repositories.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RecipeIngredientDAO {

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepo;
	
	// Getting list of RI by recipe
	public Iterable<RecipeIngredient> getAllRecipeIngredientsByRecipe (Recipe recipe)
	{
		return recipeIngredientRepo.findByRecipe(recipe);
	}
	
	// Getting list of RI by ingredient
	public Iterable<RecipeIngredient> getAllRecipeIngredientsByIngredient (Ingredient ingredient)
	{
		return recipeIngredientRepo.findByIngredient(ingredient);
	}
	
	// Save a RecipeIngredient instance
	public RecipeIngredient saveARecipeIngredient (RecipeIngredient recipeIngredient)
	{
		return recipeIngredientRepo.save(recipeIngredient);
	}

	// Finding all RecipeIngredients by both Recipe and Ingredient
	public Iterable<RecipeIngredient> getAllRecipeIngredientsByRecipeAndIngredient (Recipe recipe, Ingredient ingredient)
	{
		return recipeIngredientRepo.findByRecipeAndIngredient(recipe, ingredient);
	}

	// Deletion by Recipe
	public void deleteAllByRecipe (Recipe recipe)
	{
		recipeIngredientRepo.deleteAllByRecipe(recipe);
	}

	// Deletion by Ingredient
	public void deleteByIngredient (Ingredient ingredient)
	{
		recipeIngredientRepo.deleteByIngredient(ingredient);
	}

	// Object deletion
	public void delete (RecipeIngredient recipeIngredient)
	{
		recipeIngredientRepo.delete(recipeIngredient);
	}
}
