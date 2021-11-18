package es.lareira.spring5recipeapp.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.commands.UnitOfMeasureCommand;
import es.lareira.spring5recipeapp.converters.IngredientConverter;
import es.lareira.spring5recipeapp.converters.IngredientConverterImpl;
import es.lareira.spring5recipeapp.converters.UnitOfMeasureConverter;
import es.lareira.spring5recipeapp.converters.UnitOfMeasureConverterImpl;
import es.lareira.spring5recipeapp.domain.Ingredient;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import es.lareira.spring5recipeapp.repositories.UnitOfMeasureRepository;
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
  private UnitOfMeasureRepository unitOfMeasureRepository;

  private IngredientConverter ingredientConverter;


  @BeforeEach
  void setup() {
    UnitOfMeasureConverter unitOfMeasureConverter = new UnitOfMeasureConverterImpl();
    ingredientConverter = Mockito.spy(new IngredientConverterImpl(unitOfMeasureConverter));
    ingredientService = new IngredientServiceImpl(recipeRepository, ingredientConverter,
        unitOfMeasureRepository);
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
    when(recipeRepository.save(any())).thenReturn(recipe);
    this.ingredientService.saveIngredientCommand(ingredientCommand);
    verify(ingredientConverter, times(1)).toDomain(ingredientCommand);
    ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
    verify(recipe, times(1)).addIngredient(ingredientCaptor.capture());
    Assertions.assertEquals(ingredientId, ingredientCaptor.getValue().getId());
    verify(recipeRepository, times(1)).save(recipe);
  }

  @ParameterizedTest
  @CsvSource({
      "1, 2, 3, description",
      "3, 4, 5, description2"
  })
  void saveIngredientCommandWhenIngredientExist(Long ingredientId, Long recipeId, Long uomId,
      String description) {
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredientId);
    ingredientCommand.setRecipeId(recipeId);
    ingredientCommand.setDescription(description);
    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(uomId);
    ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
    when(unitOfMeasureRepository.findById(uomId)).thenReturn(Optional.of(new UnitOfMeasure()));
    Recipe recipe = Mockito.spy(new Recipe());
    recipe.setId(recipeId);
    Ingredient ingredient = Mockito.spy(Ingredient.builder()
        .id(ingredientId)
        .build());
    recipe.addIngredient(ingredient);
    when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
    when(recipeRepository.save(any())).thenReturn(recipe);
    this.ingredientService.saveIngredientCommand(ingredientCommand);
    verify(ingredientConverter, times(1)).toCommand(any());
    ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
    verify(recipe, times(1)).addIngredient(ingredientCaptor.capture());
    Assertions.assertEquals(ingredientId, ingredientCaptor.getValue().getId());
    verify(recipeRepository, times(1)).save(recipe);
    verify(ingredient, times(1)).setUnitOfMeasure(any());
    verify(ingredient, times(1)).setDescription(any());
    verify(ingredient, times(1)).setAmount(any());
  }
}