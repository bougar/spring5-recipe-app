package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.domain.Recipe;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    uses = {IngredientConverter.class, NotesConverter.class},
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RecipeConverter {

  RecipeCommand toCommand(Recipe recipe);

  Recipe toDomain(RecipeCommand recipeCommand);
}
