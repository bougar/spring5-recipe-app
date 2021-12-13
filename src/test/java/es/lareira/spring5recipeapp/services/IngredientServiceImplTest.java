package es.lareira.spring5recipeapp.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.converters.IngredientConverter;
import es.lareira.spring5recipeapp.converters.IngredientConverterImpl;
import es.lareira.spring5recipeapp.converters.UnitOfMeasureConverter;
import es.lareira.spring5recipeapp.converters.UnitOfMeasureConverterImpl;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.IngredientRepository;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

  private IngredientService ingredientService;

  @Mock
  private RecipeRepository recipeRepository;

  @Mock
  private IngredientRepository ingredientRepository;

  private IngredientConverter ingredientConverter;


  @BeforeEach
  void setup() {
    UnitOfMeasureConverter unitOfMeasureConverter = new UnitOfMeasureConverterImpl();
    ingredientConverter = Mockito.spy(new IngredientConverterImpl(unitOfMeasureConverter));
    ingredientService = new IngredientServiceImpl(recipeRepository, ingredientConverter,
        ingredientRepository);
  }


  @Test
  void findByRecipeIdAndIngredientIdWhenRecipeDoesNotExistsReturnNull() {
    IngredientCommand actual = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);
    verify(recipeRepository).findById(1L);
    verifyNoInteractions(ingredientConverter);
    Assertions.assertNull(actual);
  }

  @Test
  void findByRecipeIdAndIngredientIdWhenRecipeExistsButIngredientNot() {
    Ingredient ingredient = Ingredient.builder().id(2L).build();
    Recipe recipe = Recipe.builder()
        .id(1L)
        .description("description")
        .ingredients(Set.of(ingredient))
        .build();
    when(recipeRepository.findById(1L))
        .thenReturn(Optional.of(recipe));
    IngredientCommand actual = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);
    verify(recipeRepository).findById(1L);
    verifyNoInteractions(ingredientConverter);
    Assertions.assertNull(actual);
  }

  @Test
  void findByRecipeIdAndIngredientIdWhenRecipeAndIngredientExists() {
    Ingredient ingredient1 = Ingredient.builder().id(1L).build();
    Ingredient ingredient2 = Ingredient.builder().id(2L).build();
    Ingredient ingredient3 = Ingredient.builder().id(3L).build();
    Recipe recipe = Recipe.builder()
        .id(1L)
        .description("description")
        .ingredients(Set.of(ingredient1, ingredient2, ingredient3))
        .build();
    when(recipeRepository.findById(1L))
        .thenReturn(Optional.of(recipe));
    IngredientCommand actual = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);
    verify(recipeRepository).findById(1L);
    verify(ingredientConverter).toCommand(ingredient1);
    IngredientCommand expected = ingredientConverter.toCommand(ingredient1);
    Assertions.assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({
      "1, 2, description",
      "3, 4, description2"
  })
  void saveIngredientCommand(Long ingredientId, Long recipeId, String description) {
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredientId);
    ingredientCommand.setRecipeId(recipeId);
    ingredientCommand.setDescription(description);
    Recipe recipe = Mockito.spy(new Recipe());
    recipe.setId(recipeId);

    when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
    Ingredient savedIngredient = Ingredient.builder()
        .id(ingredientId)
        .recipe(recipe)
        .description(description)
        .build();
    when(ingredientRepository.save(any())).thenReturn(savedIngredient);
    IngredientCommand actual = ingredientService.saveIngredientCommand(ingredientCommand);
    ArgumentCaptor<Ingredient> ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
    verify(ingredientRepository).save(ingredientArgumentCaptor.capture());
    Assertions.assertEquals(recipe, ingredientArgumentCaptor.getValue().getRecipe());
    Assertions.assertEquals(recipeId, actual.getRecipeId());
    Assertions.assertEquals(ingredientId, actual.getId());
    Assertions.assertEquals(description, actual.getDescription());
  }

  @ParameterizedTest
  @CsvSource({"5,6", "8,14", "9,433"})
  void deleteIngredient(Long recipeId, Long ingredientId) {
    Ingredient ingredient1 = Ingredient.builder()
        .id(ingredientId)
        .build();
    Ingredient ingredient2 = Ingredient.builder()
        .id(ingredientId + 1)
        .build();
    Recipe recipe = Recipe.builder()
        .id(recipeId)
        .ingredients(Set.of(ingredient1, ingredient2))
        .build();
    when(this.recipeRepository.findById(recipeId))
        .thenReturn(Optional.of(recipe));
    this.ingredientService.deleteIngredient(recipeId, ingredientId);
    verify(this.recipeRepository, times(1)).findById(recipeId);
    verify(this.recipeRepository, times(1)).save(recipe);
    Assertions.assertEquals(1, recipe.getIngredients().size());
    Assertions.assertTrue(recipe.getIngredients().contains(ingredient2));
  }
}