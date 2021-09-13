package es.lareira.spring5recipeapp.controllers;

import java.util.Set;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

  @Mock private RecipeService recipeService;

  private IndexController indexController;

  @BeforeEach
  void setUp() {
    this.indexController = new IndexController(this.recipeService);
  }

  @SneakyThrows
  @Test
  void testMockMVC() {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));

  }

  @Test
  @SuppressWarnings("unchecked")
  void getIndexPage() {
    final Model mockModel = Mockito.mock(Model.class);
    final Set<Recipe> recipes = Set.of(new Recipe());
    when(this.recipeService.getRecipes()).thenReturn(recipes);
    final ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
    final String indexPage = this.indexController.getIndexPage(mockModel);
    verify(mockModel).addAttribute(eq("recipes"), argumentCaptor.capture());
    Assertions.assertEquals("index", indexPage);
    Assertions.assertEquals(1, argumentCaptor.getValue().size());
  }
}
