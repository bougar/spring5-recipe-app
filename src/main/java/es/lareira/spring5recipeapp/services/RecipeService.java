package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.domain.Recipe;
import java.util.Set;

public interface RecipeService {

  Set<Recipe> getRecipes();

  Recipe findRecipeById(Long id);

  RecipeCommand saveRecipeCommand(RecipeCommand command);

  RecipeCommand findRecipeCommandById(Long id);

  void deleteRecipeById(Long id);
}
