package es.lareira.spring5recipeapp.controllers;

import static org.mockito.Mockito.when;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RecipesControllerTest {

  private MockMvc mockMvc;

  @Mock
  private RecipeService recipeService;

  @BeforeEach
  public void setUp() {
    RecipesController recipesController = new RecipesController(recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(recipesController).build();
  }

  @SneakyThrows
  @Test
  void showRecipe() {
    Recipe expected = new Recipe();
    expected.setId(1L);
    when(recipeService.findRecipeById(1L)).thenReturn(expected);
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attribute("recipe", expected))
        .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
  }

}