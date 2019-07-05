package org.cookbook.clr;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.cookbook.dao.IngredientDAO;
import org.cookbook.dao.IngredientTypeDAO;
import org.cookbook.dao.RecipeDAO;
import org.cookbook.dao.RecipeIngredientDAO;
import org.cookbook.models.Ingredient;
import org.cookbook.models.IngredientType;
import org.cookbook.models.Recipe;
import org.cookbook.models.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner{
	
	@Autowired
	private IngredientDAO ingredientDao;
	@Autowired
	private IngredientTypeDAO ingredientTypeDao;
	@Autowired
	private RecipeDAO recipeDao;
	@Autowired
	private RecipeIngredientDAO recipeIngredientDao;
	
	@Override
	public void run(String... args) throws Exception {
		// Import stuff into database before the main app runs
		//
		System.out.println("CommandLineRunner run method triggered!");
		
		// Make a list of IngredientType first
		List<IngredientType> ingredientTypes = new ArrayList<IngredientType>();
		ingredientTypes.add(new IngredientType(1L, "Vegetables"));
		ingredientTypes.add(new IngredientType(2L, "Spices and Herbs"));
		ingredientTypes.add(new IngredientType(3L, "Cereals and Pulses"));
		ingredientTypes.add(new IngredientType(4L, "Meat"));
		ingredientTypes.add(new IngredientType(5L, "Dairy Products"));
		ingredientTypes.add(new IngredientType(6L, "Fruits"));
		ingredientTypes.add(new IngredientType(7L, "Seafood"));
		ingredientTypes.add(new IngredientType(8L, "Sugar and Sugar Products"));
		ingredientTypes.add(new IngredientType(9L, "Nuts and Oilseeds"));
		ingredientTypes.add(new IngredientType(10L, "Other Ingredients"));
		
		for (IngredientType it: ingredientTypes) ingredientTypeDao.saveAnIngredientType(it);
		
		// Make a list of ingredients
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(new Ingredient(1L, "Carrot", ingredientTypes.get(0)));
		ingredients.add(new Ingredient(2L, "Tommato", ingredientTypes.get(0)));
		ingredients.add(new Ingredient(3L, "Chicken breast", ingredientTypes.get(3)));
		ingredients.add(new Ingredient(4L, "Apple", ingredientTypes.get(5)));
		ingredients.add(new Ingredient(5L, "Pear", ingredientTypes.get(5)));
		
		for (Ingredient i: ingredients) ingredientDao.saveAnIngredient(i);
		
		// Making a single recipe record
		Recipe recipe = new Recipe(1L, "Vegetable and fruit with chicken breasts", 
				"A very tasty recipe which is made by mixing fresh vegetables and fruit with chicken tender meat.");
		recipe.setRecipePhoto(extractBytes("src/main/resources/static/images/addnewrecipe.jpg"));
		
		Recipe recipe2 = new Recipe(2L, "Vegetable ONLY with chicken breasts", 
				"A very tasty recipe which is made by mixing fresh vegetables ONLY with chicken tender meat.");
		recipe2.setRecipePhoto(extractBytes("src/main/resources/static/images/addnewrecipe.jpg"));
		
		recipeDao.saveRecipe(recipe);
		recipeDao.saveRecipe(recipe2);
		
		// Populating mid-table RecipeIngredient
		List<RecipeIngredient> recipeIngredients = new ArrayList<>();
		recipeIngredients.add(new RecipeIngredient(1L, "3", recipe, ingredients.get(0)));
		recipeIngredients.add(new RecipeIngredient(2L, "3", recipe, ingredients.get(1)));
		recipeIngredients.add(new RecipeIngredient(3L, "1 whole", recipe, ingredients.get(2)));
		recipeIngredients.add(new RecipeIngredient(4L, "2 peeled", recipe, ingredients.get(3)));
		recipeIngredients.add(new RecipeIngredient(5L, "2 peeled", recipe, ingredients.get(4)));
		// second recipe
		recipeIngredients.add(new RecipeIngredient(6L, "3", recipe2, ingredients.get(0)));
		recipeIngredients.add(new RecipeIngredient(7L, "3", recipe2, ingredients.get(1)));
		recipeIngredients.add(new RecipeIngredient(8L, "1 whole", recipe2, ingredients.get(2)));
		
		for (RecipeIngredient ri: recipeIngredients) recipeIngredientDao.saveARecipeIngredient(ri);
		
	}

	private byte[] extractBytes (String ImageName) throws IOException {
		 File fi = new File(ImageName);
		 byte[] fileContent = Files.readAllBytes(fi.toPath());
		 return fileContent;
	}
	
	
	
	
	
	
	
}
