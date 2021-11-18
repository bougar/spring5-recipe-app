package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Ingredient.IngredientBuilder;
import es.lareira.spring5recipeapp.domain.Recipe;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    uses = {UnitOfMeasureConverter.class},
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface IngredientConverter {

  @Mapping(target = "recipeId", ignore = true)
  IngredientCommand toCommand(Ingredient ingredient);

  @Mapping(target = "recipe", ignore = true)
  Ingredient toDomain(IngredientCommand ingredientCommand);

  @AfterMapping
  default void setRecipeIdOnCommand(Ingredient ingredient,
      @MappingTarget IngredientCommand ingredientCommand) {
    if (ingredient.getRecipe() == null) {
      return;
    }
    if (ingredient.getRecipe().getId() == null) {
      return;
    }
    ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
  }

  @AfterMapping
  default void setRecipeForIngredient(@MappingTarget IngredientBuilder builder,
      IngredientCommand ingredientCommand) {
    if (ingredientCommand.getRecipeId() == null) {
      return;
    }
    Recipe recipe = Recipe.builder().id(ingredientCommand.getRecipeId()).build();
    builder.recipe(recipe);
  }


}
