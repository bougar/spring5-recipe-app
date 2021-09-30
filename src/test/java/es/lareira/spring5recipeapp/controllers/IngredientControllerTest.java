package es.lareira.spring5recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

  private MockMvc mockMvc;

  @Mock
  private RecipeService recipeService;


  @BeforeEach
  void setUp() {
    IngredientController ingredientController = new IngredientController(recipeService);
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
}