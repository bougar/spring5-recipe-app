package es.lareira.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import es.lareira.spring5recipeapp.commands.NotesCommand;
import es.lareira.spring5recipeapp.domain.Category;
import es.lareira.spring5recipeapp.domain.Difficulty;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Notes;
import es.lareira.spring5recipeapp.domain.Recipe;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class NotesConverterTest {

  @Test
  void testToCommand() {
    NotesConverterImpl notesConverterImpl = new NotesConverterImpl();

    Recipe recipe = new Recipe();
    recipe.setDirections("Directions");
    recipe.setIngredients(new HashSet<Ingredient>());
    recipe.setServings(1);
    recipe.setCategories(new HashSet<Category>());
    recipe.setDescription("The characteristics of someone or something");
    recipe.setSource("Source");
    recipe.setImage(new Byte[]{'A'});
    recipe.setUrl("https://example.org/example");
    recipe.setId(123L);
    recipe.setDifficulty(Difficulty.EASY);
    recipe.setNotes(new Notes());
    recipe.setCookingTime(1);
    recipe.setPreparationTime(1);

    Notes notes = new Notes();
    notes.setId(123L);
    notes.setRecipeNotes("Recipe Notes");
    notes.setRecipe(recipe);

    Recipe recipe1 = new Recipe();
    recipe1.setDirections("Directions");
    recipe1.setIngredients(new HashSet<Ingredient>());
    recipe1.setServings(1);
    recipe1.setCategories(new HashSet<Category>());
    recipe1.setDescription("The characteristics of someone or something");
    recipe1.setSource("Source");
    recipe1.setImage(new Byte[]{'A'});
    recipe1.setUrl("https://example.org/example");
    recipe1.setId(123L);
    recipe1.setDifficulty(Difficulty.EASY);
    recipe1.setNotes(notes);
    recipe1.setCookingTime(1);
    recipe1.setPreparationTime(1);

    Notes notes1 = new Notes();
    notes1.setId(123L);
    notes1.setRecipeNotes("Recipe Notes");
    notes1.setRecipe(recipe1);
    NotesCommand actualToCommandResult = notesConverterImpl.toCommand(notes1);
    assertEquals(123L, actualToCommandResult.getId().longValue());
    assertEquals("Recipe Notes", actualToCommandResult.getRecipeNotes());
  }

  @Test
  void testToDomain() {
    NotesConverterImpl notesConverterImpl = new NotesConverterImpl();

    NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(123L);
    notesCommand.setRecipeNotes("Recipe Notes");
    Notes actualToDomainResult = notesConverterImpl.toDomain(notesCommand);
    assertEquals(123L, actualToDomainResult.getId().longValue());
    assertEquals("Recipe Notes", actualToDomainResult.getRecipeNotes());
  }
}

