package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.CategoryCommand;
import es.lareira.spring5recipeapp.domain.Category;
import org.mapstruct.Mapper;

@Mapper(uses = {RecipeConverter.class})
public interface CategoryConverter {

  CategoryCommand toCommand(Category category);

  Category toDomain(CategoryCommand categoryCommand);
}
