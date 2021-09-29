package es.lareira.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.commands.UnitOfMeasureCommand;
import es.lareira.spring5recipeapp.domain.Category;
import es.lareira.spring5recipeapp.domain.Difficulty;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Notes;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import java.math.BigDecimal;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class IngredientConverterTest {

  @Test
  void testToCommand() {
    IngredientConverterImpl ingredientConverterImpl = new IngredientConverterImpl();

    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(123L);
    unitOfMeasure.setDescription("The characteristics of someone or something");

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
    recipe1.setIngredients(new HashSet<>());
    recipe1.setServings(1);
    recipe1.setCategories(new HashSet<>());
    recipe1.setDescription("The characteristics of someone or something");
    recipe1.setSource("Source");
    recipe1.setImage(new Byte[]{'A'});
    recipe1.setUrl("https://example.org/example");
    recipe1.setId(123L);
    recipe1.setDifficulty(Difficulty.EASY);
    recipe1.setNotes(notes);
    recipe1.setCookingTime(1);
    recipe1.setPreparationTime(1);

    Ingredient ingredient = new Ingredient();
    ingredient.setUnitOfMeasure(unitOfMeasure);
    BigDecimal valueOfResult = BigDecimal.valueOf(42L);
    ingredient.setAmount(valueOfResult);
    ingredient.setId(123L);
    ingredient.setDescription("The characteristics of someone or something");
    ingredient.setRecipe(recipe1);
    IngredientCommand actualToCommandResult = ingredientConverterImpl.toCommand(ingredient);
    BigDecimal amount = actualToCommandResult.getAmount();
    assertSame(valueOfResult, amount);
    assertEquals("The characteristics of someone or something",
        actualToCommandResult.getDescription());
    assertEquals(123L, actualToCommandResult.getId().longValue());
    UnitOfMeasureCommand unitOfMeasure1 = actualToCommandResult.getUnitOfMeasure();
    assertEquals("The characteristics of someone or something", unitOfMeasure1.getDescription());
    assertEquals(123L, unitOfMeasure1.getId().longValue());
    assertEquals("42", amount.toString());
  }

  @Test
  void testToDomain() {
    IngredientConverterImpl ingredientConverterImpl = new IngredientConverterImpl();

    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(123L);
    unitOfMeasureCommand.setDescription("The characteristics of someone or something");

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setAmount(BigDecimal.valueOf(42L));
    ingredientCommand.setId(123L);
    ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
    ingredientCommand.setDescription("The characteristics of someone or something");
    Ingredient actualToDomainResult = ingredientConverterImpl.toDomain(ingredientCommand);
    assertEquals(
        "Ingredient(id=123, description=The characteristics of someone or something, amount=42, unitOfMeasure"
            + "=UnitOfMeasure(id=123, description=The characteristics of someone or something))",
        actualToDomainResult.toString());
    assertEquals("The characteristics of someone or something",
        actualToDomainResult.getDescription());
    assertEquals(123L, actualToDomainResult.getId().longValue());
    UnitOfMeasure unitOfMeasure = actualToDomainResult.getUnitOfMeasure();
    assertEquals(123L, unitOfMeasure.getId().longValue());
    assertEquals("The characteristics of someone or something", unitOfMeasure.getDescription());
    assertEquals("42", actualToDomainResult.getAmount().toString());
  }
}

