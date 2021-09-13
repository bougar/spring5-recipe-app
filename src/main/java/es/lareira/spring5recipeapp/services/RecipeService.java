package es.lareira.spring5recipeapp.services;

import java.util.Set;

import es.lareira.spring5recipeapp.domain.Recipe;

public interface RecipeService {
  Set<Recipe> getRecipes();
}
