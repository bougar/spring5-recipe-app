package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.converters.IngredientConverter;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.IngredientRepository;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

  private final RecipeRepository recipeRepository;

  private final IngredientConverter ingredientConverter;

  private final IngredientRepository ingredientRepository;

  public IngredientServiceImpl(
      RecipeRepository recipeRepository,
      IngredientConverter ingredientConverter,
      IngredientRepository ingredientRepository) {
    this.recipeRepository = recipeRepository;
    this.ingredientConverter = ingredientConverter;
    this.ingredientRepository = ingredientRepository;
  }

  private Optional<Ingredient> findIngredientById(Set<Ingredient> ingredients, Long ingredientId) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientId))
        .findFirst();
  }

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
    Optional<Recipe> recipe = recipeRepository.findById(recipeId);
    if (recipe.isEmpty()) {
      log.error("Recipe with with id [{}] not found", recipeId);
      return null;
    }
    Set<Ingredient> ingredients = recipe.get().getIngredients();
    Optional<Ingredient> ingredient = findIngredientById(ingredients, ingredientId);
    if (ingredient.isEmpty()) {
      log.error("Ingredient with id [{}] not found for recipe [{}]", ingredientId, recipeId);
      return null;
    }
    return ingredientConverter.toCommand(ingredient.get());
  }

  @Override
  public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
    Recipe recipe = recipeOptional.orElseThrow(() -> new RuntimeException("Recipe Not Found"));
    Ingredient ingredient = ingredientConverter.toDomain(ingredientCommand);
    ingredient.setRecipe(recipe);
    Ingredient savedIngredient = ingredientRepository.save(ingredient);
    recipe.addIngredient(savedIngredient);
    recipeRepository.save(recipe);
    return ingredientConverter.toCommand(savedIngredient);
  }

  @Override
  public void deleteIngredient(Long recipeId, Long ingredientId) {
    Optional<Recipe> optionalRecipe = this.recipeRepository.findById(recipeId);
    Recipe recipe = optionalRecipe.orElseThrow(() -> new RuntimeException("Recipe does not exist"));
    HashSet<Ingredient> ingredients = new HashSet<>(recipe.getIngredients());
    ingredients.removeIf(ingredient -> ingredient.getId().equals(ingredientId));
    recipe.setIngredients(ingredients);
    ingredientRepository.deleteById(ingredientId);
  }
}
