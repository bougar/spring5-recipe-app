package es.lareira.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.converters.IngredientConverterImpl;
import es.lareira.spring5recipeapp.converters.NotesConverterImpl;
import es.lareira.spring5recipeapp.converters.RecipeConverter;
import es.lareira.spring5recipeapp.converters.RecipeConverterImpl;
import es.lareira.spring5recipeapp.converters.UnitOfMeasureConverterImpl;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.exceptions.NotFoundException;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

  private RecipeServiceImpl recipeService;

  private RecipeConverter recipeConverter;

  @Mock
  private RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    recipeConverter = Mockito.spy(
        new RecipeConverterImpl(new IngredientConverterImpl(new UnitOfMeasureConverterImpl()),
            new NotesConverterImpl()));
    this.recipeService = new RecipeServiceImpl(this.recipeRepository, recipeConverter);
  }

  @Test
  void getRecipes() {
    when(this.recipeRepository.findAll()).thenReturn(Collections.singletonList(new Recipe()));
    final Set<Recipe> recipes = this.recipeService.getRecipes();
    Assertions.assertEquals(1, recipes.size());
    verify(this.recipeRepository, times(1)).findAll();
  }

  @Test
  void findRecipeById() {
    Recipe expected = new Recipe();
    expected.setId(1L);
    expected.setDescription("description");
    when(recipeRepository.findById(1L)).thenReturn(Optional.of(expected));

    Recipe actual = recipeService.findRecipeById(1L);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void findRecipeByThrowsRunTimeExceptionIfRecipeDoesNotExists() {
    assertThrows(RuntimeException.class, () -> recipeService.findRecipeById(2L));
  }

  @Test
  void saveRecipeCommand() {
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(5L);
    Recipe savedRecipe = new Recipe();
    savedRecipe.setId(7L);
    when(recipeRepository.save(any())).thenReturn(savedRecipe);
    RecipeCommand actual = recipeService.saveRecipeCommand(recipeCommand);
    ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);
    verify(recipeRepository, times(1)).save(captor.capture());
    Assertions.assertEquals(5L, captor.getValue().getId());
    Assertions.assertEquals(7L, actual.getId());
    verify(recipeConverter, times(1)).toCommand(savedRecipe);
  }

  @Test
  void findRecipeCommandById() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    recipe.setDescription("some description");
    recipe.setSource("some source");
    when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
    RecipeCommand actual = recipeService.findRecipeCommandById(1L);
    Assertions.assertEquals(1L, actual.getId());
    Assertions.assertEquals("some description", actual.getDescription());
    Assertions.assertEquals("some source", actual.getSource());
    verify(recipeConverter, times(1)).toCommand(recipe);
  }

  @ParameterizedTest
  @ValueSource(longs = {1, 2, 434, 43434})
  void findRecipeByIdNotFound(Long id) {
    when(recipeRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> this.recipeService.findRecipeById(id));
  }

  @Test
  void deleteById() {
    recipeService.deleteRecipeById(2L);
    verify(recipeRepository, times(1)).deleteById(2L);
    recipeService.deleteRecipeById(5L);
    verify(recipeRepository, times(1)).deleteById(5L);
  }
}
