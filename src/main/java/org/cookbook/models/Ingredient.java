package org.cookbook.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ingredient_id")
	private Long ingredientId;
	@Column(name="ingredient_title")
	private String ingredientTitle;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ingredient_type_id")
	private IngredientType ingredientType;
	
	@OneToMany(mappedBy = "ingredient")
	private List<RecipeIngredient> recipeIngredients;

	public Ingredient(Long ingredientId, String ingredientTitle, IngredientType ingredientType,
			List<RecipeIngredient> recipeIngredients) {
		super();
		this.ingredientId = ingredientId;
		this.ingredientTitle = ingredientTitle;
		this.ingredientType = ingredientType;
		this.recipeIngredients = recipeIngredients;
	}

	public Ingredient(Long ingredientId, String ingredientTitle, IngredientType ingredientType) {
		super();
		this.ingredientId = ingredientId;
		this.ingredientTitle = ingredientTitle;
		this.ingredientType = ingredientType;
	}

	public Ingredient() {
		super();
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getIngredientTitle() {
		return ingredientTitle;
	}

	public void setIngredientTitle(String ingredientTitle) {
		this.ingredientTitle = ingredientTitle;
	}

	public IngredientType getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(IngredientType ingredientType) {
		this.ingredientType = ingredientType;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	
}
