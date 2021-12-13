package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {

  IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

  IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

  void deleteIngredient(Long recipeId, Long ingredientId);
}
