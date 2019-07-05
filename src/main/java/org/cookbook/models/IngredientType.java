package org.cookbook.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ingredienttype")
public class IngredientType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ingredient_type_id")
	private Long ingredientTypeId;
	@Column(name="ingredient_type")
	private String ingredientType;
	
	public IngredientType(Long ingredientTypeId, String ingredientType) {
		super();
		this.ingredientTypeId = ingredientTypeId;
		this.ingredientType = ingredientType;
	}
	
	public IngredientType() {
		super();
	}

	public Long getIngredientTypeId() {
		return ingredientTypeId;
	}

	public void setIngredientTypeId(Long ingredientTypeId) {
		this.ingredientTypeId = ingredientTypeId;
	}

	public String getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(String ingredientType) {
		this.ingredientType = ingredientType;
	}
	
	
}