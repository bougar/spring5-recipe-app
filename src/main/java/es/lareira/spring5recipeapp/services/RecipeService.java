package es.lareira.spring5recipeapp.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;

  public RecipeService(final RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Set<Recipe> getRecipes() {
    final Set<Recipe> recipeList = new HashSet<>();
    final Iterable<Recipe> iterableRecipes = this.recipeRepository.findAll();
    iterableRecipes.forEach(recipeList::add);
    return recipeList;
  }
}
