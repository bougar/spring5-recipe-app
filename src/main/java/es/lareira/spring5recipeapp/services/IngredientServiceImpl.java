package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.converters.IngredientConverter;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import es.lareira.spring5recipeapp.repositories.UnitOfMeasureRepository;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

  private final RecipeRepository recipeRepository;

  private final IngredientConverter ingredientConverter;

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public IngredientServiceImpl(
      RecipeRepository recipeRepository,
      IngredientConverter ingredientConverter,
      UnitOfMeasureRepository unitOfMeasureRepository) {
    this.recipeRepository = recipeRepository;
    this.ingredientConverter = ingredientConverter;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
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
    if (recipeOptional.isEmpty()) {
      log.error("Recipe with id [{}] not found", ingredientCommand.getRecipeId());
      return new IngredientCommand();
    }
    Recipe recipe = recipeOptional.get();
    Optional<Ingredient> optionalIngredient = recipe.getIngredients()
        .stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
        .findFirst();
    if (optionalIngredient.isPresent()) {
      Ingredient ingredient = optionalIngredient.get();
      ingredient.setDescription(ingredientCommand.getDescription());
      ingredient.setAmount(ingredientCommand.getAmount());
      UnitOfMeasure unitOfMeasure = this.unitOfMeasureRepository.findById(
              ingredientCommand.getUnitOfMeasure().getId())
          .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"));
      ingredient.setUnitOfMeasure(unitOfMeasure);
    } else {
      recipe.addIngredient(ingredientConverter.toDomain(ingredientCommand));
    }
    Recipe savedRecipe = this.recipeRepository.save(recipe);
    Ingredient savedIngredient = savedRecipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Ingredient Not Found"));
    return ingredientConverter.toCommand(savedIngredient);
  }
}
