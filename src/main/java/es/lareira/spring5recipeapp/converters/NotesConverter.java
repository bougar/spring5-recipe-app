package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.NotesCommand;
import es.lareira.spring5recipeapp.domain.Notes;
import org.mapstruct.Mapper;

@Mapper(uses = {RecipeConverter.class})
public interface NotesConverter {

  NotesCommand toCommand(Notes notes);

  Notes toDomain(NotesCommand notesCommand);

}
