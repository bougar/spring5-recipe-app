package es.lareira.spring5recipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.exceptions.NotFoundException;
import es.lareira.spring5recipeapp.services.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
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
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attribute("recipe", expected))
        .andExpect(view().name("recipe/show"));
  }

  @ParameterizedTest
  @SneakyThrows
  @ValueSource(longs = {1, 2, 434, 5344})
  void showRecipeWhenNotFound(Long recipeId) {
    when(recipeService.findRecipeById(recipeId))
        .thenThrow(new NotFoundException("Recipe Not Found"));
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/show"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(view().name("NotFoundPage"));
  }

  @SneakyThrows
  @Test
  void getCreateRecipeForm() {
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
        .andExpect(view().name("recipe/recipeform"));
  }

  @SneakyThrows
  @Test
  void createRecipeFormPost() {
    RecipeCommand expected = new RecipeCommand();
    expected.setId(3L);
    when(recipeService.saveRecipeCommand(any())).thenReturn(expected);
    mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "3")
            .param("description", "some description")
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/3/show"));
    ArgumentCaptor<RecipeCommand> captor = ArgumentCaptor.forClass(RecipeCommand.class);
    verify(recipeService, times(1)).saveRecipeCommand(captor.capture());
    Assertions.assertEquals(3L, captor.getValue().getId());
    Assertions.assertEquals("some description", captor.getValue().getDescription());
  }

  @SneakyThrows
  @Test
  void getUpdateView() {
    when(recipeService.findRecipeCommandById(7L)).thenReturn(new RecipeCommand());
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/7/update"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
        .andExpect(view().name("recipe/recipeform"));
    verify(recipeService, times(1)).findRecipeCommandById(7L);
  }

  @SneakyThrows
  @Test
  void deleteRecipe() {
    mockMvc.perform(MockMvcRequestBuilders.post("/recipe/1/delete"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    verify(recipeService, times(1)).deleteRecipeById(1L);
  }
}