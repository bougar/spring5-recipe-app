package es.lareira.spring5recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.IngredientService;
import es.lareira.spring5recipeapp.services.RecipeService;
import es.lareira.spring5recipeapp.services.UnitOfMeasurementService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

  private MockMvc mockMvc;

  @Mock
  private IngredientService ingredientService;

  @Mock
  private RecipeService recipeService;

  @Mock
  private UnitOfMeasurementService unitOfMeasurementService;

  @BeforeEach
  void setUp() {
    IngredientController ingredientController = new IngredientController(recipeService,
        ingredientService, unitOfMeasurementService);
    mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
  }

  @Test
  @SneakyThrows
  void listIngredients() {
    Recipe expected = new Recipe();
    when(recipeService.findRecipeById(1L)).thenReturn(expected);
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredients/list"))
        .andExpect(model().attribute("recipe", expected));
    verify(recipeService, times(1)).findRecipeById(1L);
  }

  @Test
  @SneakyThrows
  void showIngredient() {
    when(ingredientService.findByRecipeIdAndIngredientId(3L, 7L))
        .thenReturn(new IngredientCommand());
    mockMvc.perform(get("/recipe/3/ingredients/7/show"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(view().name("recipe/ingredients/show"));
    verify(ingredientService).findByRecipeIdAndIngredientId(3L, 7L);
  }

  @Test
  @SneakyThrows
  void updateRecipeIngredient() {
    when(ingredientService.findByRecipeIdAndIngredientId(3L, 7L))
        .thenReturn(new IngredientCommand());
    mockMvc.perform(get("/recipe/3/ingredients/7/update"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"))
        .andExpect(view().name("recipe/ingredients/updateForm"));
    verify(ingredientService).findByRecipeIdAndIngredientId(3L, 7L);
  }

  @Test
  @SneakyThrows
  void newRecipeIngredient() {
    when(recipeService.findRecipeCommandById(3L))
        .thenReturn(new RecipeCommand());
    mockMvc.perform(get("/recipe/3/ingredients/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"))
        .andExpect(view().name("recipe/ingredients/updateForm"));
  }

  @SneakyThrows
  @ParameterizedTest
  @CsvSource({"5,7", "8,15", "24,43"})
  void deleteIngredient(String recipeId, String ingredientId) {
    mockMvc.perform(post("/recipe/" + recipeId + "/ingredients/" + ingredientId + "/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + recipeId));
    verify(ingredientService, times(1))
        .deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
  }
}