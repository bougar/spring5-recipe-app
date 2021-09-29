package es.lareira.spring5recipeapp.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.lareira.spring5recipeapp.converters.IngredientConverterImpl;
import es.lareira.spring5recipeapp.converters.NotesConverterImpl;
import es.lareira.spring5recipeapp.converters.RecipeConverterImpl;
import es.lareira.spring5recipeapp.converters.UnitOfMeasureConverterImpl;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

  private RecipeServiceImpl recipeService;

  @Mock
  private RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    this.recipeService = new RecipeServiceImpl(this.recipeRepository,
        new RecipeConverterImpl(new IngredientConverterImpl(new UnitOfMeasureConverterImpl()),
            new NotesConverterImpl()));
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
    Assertions.assertThrows(RuntimeException.class, () -> recipeService.findRecipeById(2L));
  }
}
