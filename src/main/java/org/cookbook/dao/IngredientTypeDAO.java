package org.cookbook.dao;

import org.cookbook.models.IngredientType;
import org.cookbook.repositories.IngredientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientTypeDAO {

	@Autowired
	private IngredientTypeRepository ingredientTypeRepo;
	
	// Read all
	public Iterable<IngredientType> getAllIngredientTypes ()
	{
		return ingredientTypeRepo.findAll();
	}
	
	// Save an ingredient type
	public IngredientType saveAnIngredientType (IngredientType ingredientType)
	{
		return ingredientTypeRepo.save(ingredientType);
	}
}
