package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.domain.Ingredient;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    uses = {UnitOfMeasureConverter.class},
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface IngredientConverter {

  IngredientCommand toCommand(Ingredient ingredient);

  Ingredient toDomain(IngredientCommand ingredientCommand);

}
