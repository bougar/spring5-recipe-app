package es.lareira.spring5recipeapp.services;

import java.util.Collections;
import java.util.Set;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

  private RecipeServiceImpl recipeService;

  @Mock private RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    this.recipeService = new RecipeServiceImpl(this.recipeRepository);
  }

  @Test
  void getRecipes() {
    when(this.recipeRepository.findAll()).thenReturn(Collections.singletonList(new Recipe()));
    final Set<Recipe> recipes = this.recipeService.getRecipes();
    Assertions.assertEquals(1, recipes.size());
    verify(this.recipeRepository, times(1)).findAll();
  }
}
