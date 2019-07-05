package org.cookbook.dao;

import java.util.Optional;

import org.cookbook.models.Ingredient;
import org.cookbook.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientDAO {

	@Autowired
	private IngredientRepository ingredientRepo;
	
	// Read all ingredients
	public Iterable<Ingredient> findAllIngredients ()
	{
		return ingredientRepo.findAll();
	}
	
	// Find ingredients by ID
	public Optional<Ingredient> findIngredientById (Long id)
	{
		return ingredientRepo.findById(id);
	}
	
	// Save an ingredient
	public Ingredient saveAnIngredient (Ingredient ingredient)
	{
		return ingredientRepo.save(ingredient);
	}
	
}
