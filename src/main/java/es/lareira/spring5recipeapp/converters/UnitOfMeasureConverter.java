package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.UnitOfMeasureCommand;
import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitOfMeasureConverter {

  UnitOfMeasureCommand toCommand(UnitOfMeasure unitOfMeasure);

  UnitOfMeasure toDomain(UnitOfMeasureCommand unitOfMeasureCommand);

}
