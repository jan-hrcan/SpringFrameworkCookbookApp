package org.cookbook.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.cookbook.dao.RecipeDAO;
import org.cookbook.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomepageController {

	@Autowired
	private RecipeDAO recipeDao;
	
	@GetMapping
	public String getHomepage (Model model)
	{	
		return "homepage";
	}
}
