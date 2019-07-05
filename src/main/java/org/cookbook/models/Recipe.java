package org.cookbook.models;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="recipe_id")
	private Long recipeId;
	@Column(name="recipe_title")
	private String recipeTitle;
	@Column(name="recipe_description")
	private String recipeDescription;
	@Column(name="recipe_photo", length=100000)
	private byte[] recipePhoto;
	@Column(name="recipe_instruction")
	private String recipeInstruction;
	@Column(name="recipe_time_needed")
	private Long recipeTimeNeeded;
    @Transient
    private String imgUtility;
	
	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RecipeIngredient> recipeIngredients;

	public Recipe(Long recipeId, String recipeTitle, String recipeDescription, byte[] recipePhoto,
			String recipeInstruction, Long recipeTimeNeeded, List<RecipeIngredient> recipeIngredients) {
		super();
		this.recipeId = recipeId;
		this.recipeTitle = recipeTitle;
		this.recipeDescription = recipeDescription;
		this.recipePhoto = recipePhoto;
		this.recipeInstruction = recipeInstruction;
		this.recipeTimeNeeded = recipeTimeNeeded;
		this.recipeIngredients = recipeIngredients;
	}

	public Recipe(Long recipeId, String recipeTitle, String recipeDescription) {
		super();
		this.recipeId = recipeId;
		this.recipeTitle = recipeTitle;
		this.recipeDescription = recipeDescription;
	}

	public Recipe() {
		super();
	}

	//getter method for encoding
	public String getImgUtility() throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(recipePhoto);            
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getRecipeDescription() {
		return recipeDescription;
	}

	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}

	public byte[] getRecipePhoto() {
		return recipePhoto;
	}

	public void setRecipePhoto(byte[] recipePhoto) {
		this.recipePhoto = recipePhoto;
	}

	public String getRecipeInstruction() {
		return recipeInstruction;
	}

	public void setRecipeInstruction(String recipeInstruction) {
		this.recipeInstruction = recipeInstruction;
	}

	public Long getRecipeTimeNeeded() {
		return recipeTimeNeeded;
	}

	public void setRecipeTimeNeeded(Long recipeTimeNeeded) {
		this.recipeTimeNeeded = recipeTimeNeeded;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	
}
