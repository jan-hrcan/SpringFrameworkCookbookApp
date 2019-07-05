package org.cookbook.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

import ch.qos.logback.core.CoreConstants;
import org.cookbook.dao.IngredientDAO;
import org.cookbook.dao.RecipeDAO;
import org.cookbook.dao.RecipeIngredientDAO;
import org.cookbook.models.Ingredient;
import org.cookbook.models.Recipe;
import org.cookbook.models.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
	
	@Autowired
	private IngredientDAO ingredientDao;
	@Autowired
	private RecipeIngredientDAO recipeIngredientDao;
	@Autowired
	private RecipeDAO recipeDao;

	private List<IngredientWithMeasurement> ingredientsWithMeasurements;
	
	@GetMapping
	public String getRecipes (Model model)
	{
		// Getting all recipes and putting it inside a model
		Iterable<Recipe> recipes = recipeDao.getAllRecipes();	
		model.addAttribute("recipes", recipes);
		
		return "recipes";
	}

	@GetMapping("/create")
	public String getCreate(Model model)
	{
		Recipe recipe = new Recipe();
		Iterable<Ingredient> ingredients = ingredientDao.findAllIngredients();
		List<Ingredient> ingredientsList = (List<Ingredient>) ingredients;
		String ingredientMeasurement = null;
		if (this.ingredientsWithMeasurements != null) this.ingredientsWithMeasurements.clear();

		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredients", ingredientsList);
		model.addAttribute("ingredientMeasurement", ingredientMeasurement);
		
		return "createrecipe";
	}
	
	@GetMapping("/edit/{id}")
	public String getObjectForEdit(@PathVariable("id") String id, Model model)
	{
		Optional<Recipe> recipe = recipeDao.findByID(Long.valueOf(id));
		Iterable<Ingredient> ingredients = ingredientDao.findAllIngredients();
		List<Ingredient> ingredientsList = (List<Ingredient>) ingredients;

		Iterable<RecipeIngredient> recipeIngredients = recipeIngredientDao.getAllRecipeIngredientsByRecipe(recipe.get());

		if (ingredientsWithMeasurements == null) {
			ingredientsWithMeasurements = new ArrayList<>();
		} else {
			ingredientsWithMeasurements.clear();
		}

		for (RecipeIngredient ri: recipeIngredients)
		{
			ingredientsWithMeasurements.add(new IngredientWithMeasurement(ri.getIngredient(), ri.getMeasurement()));
		}

		String ingredientMeasurement = null;
		
		model.addAttribute("recipe", recipe.isPresent() ? recipe.get() : null);
		model.addAttribute("ingredients", ingredientsList);
		model.addAttribute("ingredientMeasurement", ingredientMeasurement);
		model.addAttribute("ingredientsWithMeasurements", ingredientsWithMeasurements);
		
		return "editrecipe";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteARecipe(@PathVariable("id") String id)
	{
		// deleting a recipe
		recipeDao.deleteRecipe(Long.valueOf(id));
		
		return "redirect:/recipes";
	}
	
	@PostMapping("/create/addingredient")
	public ResponseEntity<String> addIngredient(
			@RequestParam(value = "ingredient", required = false) Integer ingredient,
			@RequestParam(value = "ingredientMeasurement", required = false) String measurement)
	{	
		// checking it with syso
		System.out.println("Ingredient added: (ID) " + ingredient + "; Ingredient measurement: " + measurement);
		
		// checking if map is null
		if (this.ingredientsWithMeasurements == null) this.ingredientsWithMeasurements = new ArrayList<>();
		
		// Getting ingredient from db
		Optional<Ingredient> selectedIngredient = ingredientDao.findIngredientById((long) ingredient);
		
		// Adding a new ingredient
		IngredientWithMeasurement iwm = new IngredientWithMeasurement(selectedIngredient.isPresent() ? selectedIngredient.get() : null, measurement);
		this.ingredientsWithMeasurements.add(iwm);
		
		// returning an entity
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PostMapping("/removeingredient")
	public ResponseEntity<String> removeIngredient(@RequestParam(value = "id") Integer id)
	{
		// handle removal from list
		ListIterator<IngredientWithMeasurement> iterator = ingredientsWithMeasurements.listIterator();

		while (iterator.hasNext())
		{
			if (iterator.next().getIngredient().getIngredientId() == id.longValue())
			{
				iterator.remove();
				return new ResponseEntity<String>(HttpStatus.OK); // returning OKAY
			}
		}

		// returning an entity with bad request
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/create")
	public String postRecipe (
			@ModelAttribute("recipe") Recipe recipe,
			@RequestParam(value = "choosePhoto", required = false) MultipartFile photo) throws IOException
	{
		// save the MultiPart file (add it to the recipe object)
		byte[] photoBytes = photo.getBytes();		
		recipe.setRecipePhoto(photoBytes);
		
		// save an instance of Recipe object
		recipeDao.saveRecipe(recipe);
		
		// save ingredients for that recipe
		for (IngredientWithMeasurement iwm: ingredientsWithMeasurements)
		{
			recipeIngredientDao.saveARecipeIngredient(new RecipeIngredient(iwm.getMeasurement(), recipe, iwm.getIngredient()));
		}
		
		// clear the hash map
		ingredientsWithMeasurements.clear();
		
		return "redirect:/recipes";
	}

	@PostMapping("/edit/{id}")
	private String editRecipe (
			@PathVariable("id") String id,
			@ModelAttribute("recipe") Recipe recipe,
			@RequestParam(value = "choosePhoto", required = false) MultipartFile photo,
			BindingResult bindingResult) throws IOException
	{
		System.out.println(recipe.getRecipeTitle());
		// get the recipe from database
		Optional<Recipe> dbRecipe = recipeDao.findByID(Long.valueOf(id));

		// check if recipe exists and continue
		if (dbRecipe.isPresent() && recipe != null) {
			// Checking if all the fields are null
			if (recipe.getRecipeTitle() != null)
				dbRecipe.get().setRecipeTitle(recipe.getRecipeTitle());
			if (recipe.getRecipeDescription() != null)
				dbRecipe.get().setRecipeDescription(recipe.getRecipeDescription());
			if (recipe.getRecipeInstruction() != null)
				dbRecipe.get().setRecipeInstruction(recipe.getRecipeInstruction());
			if (recipe.getRecipeTimeNeeded() != null)
				dbRecipe.get().setRecipeTimeNeeded(recipe.getRecipeTimeNeeded());

			// Special check for a photo
			if (recipe.getRecipePhoto() != null) {
				byte[] photoBytes = photo.getBytes();
				if (photoBytes != dbRecipe.get().getRecipePhoto())
					dbRecipe.get().setRecipePhoto(photoBytes);
			}

			// UPDATE THE RECIPE AT THE END
			recipeDao.saveRecipe(dbRecipe.get());

			// A simpler way of handling the many to many list
			// 1. delete all RecipeIngredients
			// 2. add all of them again

			// deletion
			recipeIngredientDao.deleteAllByRecipe(dbRecipe.get());

			// addition
			// save ingredients for that recipe
			for (IngredientWithMeasurement iwm : ingredientsWithMeasurements) {
				recipeIngredientDao.saveARecipeIngredient(new RecipeIngredient(iwm.getMeasurement(), dbRecipe.get(), iwm.getIngredient()));
			}

			// clear the list
			ingredientsWithMeasurements.clear();

			// This is one way of doing it
			// NOT YET FINISHED

/*			// Checking all the ingredients
			if (this.ingredientsWithMeasurements != null)
			{
				// delete ingredients that are deleted on the front end
				for (RecipeIngredient ri: dbRecipe.get().getRecipeIngredients())
				{
					boolean found = false;
					// 1. check if the ingredient is already present in the database
					for (IngredientWithMeasurement iwm: ingredientsWithMeasurements)
					{
						if (ri.getIngredient().equals(iwm.getIngredient()))
						{
							found = true;
							break;
						}
					}

					// if not found delete it
					if (!found)
					recipeIngredientDao.delete(ri);


					recipeIngredientDao.saveARecipeIngredient(new RecipeIngredient(iwm.getMeasurement(), recipe, iwm.getIngredient()));
				}

				// 1. check ingredient measurement changes
				// 2. check if non-existant - if so, persist

				for (IngredientWithMeasurement iwm: ingredientsWithMeasurements)
				{

				}*/
		}
		return "redirect:/recipes";
	}
}

class IngredientWithMeasurement {
	private Ingredient ingredient;
	private String measurement;

	public IngredientWithMeasurement(Ingredient ingredient, String measurement) {
		this.ingredient = ingredient;
		this.measurement = measurement;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
}