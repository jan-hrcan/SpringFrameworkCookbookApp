package org.cookbook.dao;

import java.util.Optional;

import org.cookbook.models.Recipe;
import org.cookbook.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeDAO {

	@Autowired
	private RecipeRepository recipeRepo;
	
	// Read
	public Iterable<Recipe> getAllRecipes ()
	{
		return recipeRepo.findAll();
	}
	
	// Save and update
	public Recipe saveRecipe (Recipe recipe)
	{
		return recipeRepo.save(recipe);
	}
	
	// Delete by id
	public void deleteRecipe (Long id)
	{
		recipeRepo.deleteById(id);
	}
	
	// Delete by object
	public void deleteRecipe (Recipe recipe)
	{
		recipeRepo.delete(recipe);
	}
	
	// Find recipe by ID
	public Optional<Recipe> findByID (long id)
	{
		return recipeRepo.findById(id);
	}
}
