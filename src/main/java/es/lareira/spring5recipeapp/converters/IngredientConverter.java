package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.domain.Ingredient;
import org.mapstruct.Mapper;

@Mapper(uses = {UnitOfMeasureConverter.class})
public interface IngredientConverter {

  IngredientCommand toCommand(Ingredient ingredient);

  Ingredient toDomain(IngredientCommand ingredientCommand);

}
