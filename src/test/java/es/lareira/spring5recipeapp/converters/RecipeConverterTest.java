package es.lareira.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import es.lareira.spring5recipeapp.commands.NotesCommand;
import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.domain.Difficulty;
import es.lareira.spring5recipeapp.domain.Notes;
import es.lareira.spring5recipeapp.domain.Recipe;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class RecipeConverterTest {

  @Test
  void testToCommand() {
    RecipeConverterImpl recipeConverterImpl = new RecipeConverterImpl(
        new IngredientConverterImpl(new UnitOfMeasureConverterImpl()), new NotesConverterImpl());

    Notes notes = new Notes();
    notes.setId(123L);
    notes.setRecipeNotes("Recipe Notes");
    notes.setRecipe(new Recipe());

    Recipe recipe = new Recipe();
    recipe.setDirections("Directions");
    recipe.setIngredients(new HashSet<>());
    recipe.setServings(1);
    recipe.setCategories(new HashSet<>());
    recipe.setDescription("The characteristics of someone or something");
    recipe.setSource("Source");
    recipe.setImage(new Byte[]{'A'});
    recipe.setUrl("https://example.org/example");
    recipe.setId(123L);
    recipe.setDifficulty(Difficulty.EASY);
    recipe.setNotes(notes);
    recipe.setCookingTime(1);
    recipe.setPreparationTime(1);

    Notes notes1 = new Notes();
    notes1.setId(123L);
    notes1.setRecipeNotes("Recipe Notes");
    notes1.setRecipe(recipe);

    Recipe recipe1 = new Recipe();
    recipe1.setDirections("Directions");
    recipe1.setIngredients(new HashSet<>());
    recipe1.setServings(1);
    recipe1.setCategories(new HashSet<>());
    recipe1.setDescription("The characteristics of someone or something");
    recipe1.setSource("Source");
    recipe1.setImage(new Byte[]{'A'});
    recipe1.setUrl("https://example.org/example");
    recipe1.setId(123L);
    recipe1.setDifficulty(Difficulty.EASY);
    recipe1.setNotes(notes1);
    recipe1.setCookingTime(1);
    recipe1.setPreparationTime(1);
    RecipeCommand actualToCommandResult = recipeConverterImpl.toCommand(recipe1);
    assertTrue(actualToCommandResult.getCategories().isEmpty());
    assertEquals("https://example.org/example", actualToCommandResult.getUrl());
    assertEquals("Source", actualToCommandResult.getSource());
    assertEquals(1, actualToCommandResult.getServings().intValue());
    assertEquals(1, actualToCommandResult.getPreparationTime().intValue());
    assertTrue(actualToCommandResult.getIngredients().isEmpty());
    assertEquals(1, actualToCommandResult.getCookingTime().intValue());
    assertEquals(Difficulty.EASY, actualToCommandResult.getDifficulty());
    assertEquals(123L, actualToCommandResult.getId().longValue());
    assertEquals("Directions", actualToCommandResult.getDirections());
    assertEquals("The characteristics of someone or something",
        actualToCommandResult.getDescription());
    NotesCommand notes2 = actualToCommandResult.getNotes();
    assertEquals("Recipe Notes", notes2.getRecipeNotes());
    assertEquals(123L, notes2.getId().longValue());
  }

  @Test
  void testToDomain() {
    RecipeConverterImpl recipeConverterImpl = new RecipeConverterImpl(
        new IngredientConverterImpl(new UnitOfMeasureConverterImpl()), new NotesConverterImpl());

    NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(123L);
    notesCommand.setRecipeNotes("Recipe Notes");

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setDirections("Directions");
    recipeCommand.setUrl("https://example.org/example");
    recipeCommand.setId(123L);
    recipeCommand.setIngredients(new HashSet<>());
    recipeCommand.setNotes(notesCommand);
    recipeCommand.setDifficulty(Difficulty.EASY);
    recipeCommand.setServings(1);
    recipeCommand.setCategories(new HashSet<>());
    recipeCommand.setCookingTime(1);
    recipeCommand.setDescription("The characteristics of someone or something");
    recipeCommand.setSource("Source");
    recipeCommand.setPreparationTime(1);
    Recipe actualToDomainResult = recipeConverterImpl.toDomain(recipeCommand);
    assertTrue(actualToDomainResult.getCategories().isEmpty());
    assertEquals(
        "Recipe(id=123, description=The characteristics of someone or something, preparationTime=1, cookingTime=1,"
            + " servings=1, source=Source, url=https://example.org/example, directions=Directions, image=null,"
            + " difficulty=EASY, ingredients=[], notes=Notes(id=123, recipeNotes=Recipe Notes), categories=[])",
        actualToDomainResult.toString());
    assertEquals("https://example.org/example", actualToDomainResult.getUrl());
    assertEquals("Source", actualToDomainResult.getSource());
    assertEquals(1, actualToDomainResult.getServings().intValue());
    assertEquals(1, actualToDomainResult.getPreparationTime().intValue());
    assertTrue(actualToDomainResult.getIngredients().isEmpty());
    assertEquals(1, actualToDomainResult.getCookingTime().intValue());
    assertEquals(Difficulty.EASY, actualToDomainResult.getDifficulty());
    assertEquals("Directions", actualToDomainResult.getDirections());
    assertEquals("The characteristics of someone or something",
        actualToDomainResult.getDescription());
    assertEquals(123L, actualToDomainResult.getId().longValue());
    Notes notes = actualToDomainResult.getNotes();
    assertEquals("Recipe Notes", notes.getRecipeNotes());
    assertEquals(123L, notes.getId().longValue());
  }

}