package org.cookbook.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="recipeingredient")
public class RecipeIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="recipe_ingredient_id")
	private Long recipeIngredientId;
	@Column(name="measurement")
	private String measurement;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;
	
	public RecipeIngredient(Long recipeIngredientId, String measurement, Recipe recipe, Ingredient ingredient) {
		super();
		this.recipeIngredientId = recipeIngredientId;
		this.measurement = measurement;
		this.recipe = recipe;
		this.ingredient = ingredient;
	}
	
	public RecipeIngredient(String measurement, Recipe recipe, Ingredient ingredient) {
		super();
		this.measurement = measurement;
		this.recipe = recipe;
		this.ingredient = ingredient;
	}

	public RecipeIngredient() {
		super();
	}

	public Long getRecipeIngredientId() {
		return recipeIngredientId;
	}

	public void setRecipeIngredientId(Long recipeIngredientId) {
		this.recipeIngredientId = recipeIngredientId;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
}
