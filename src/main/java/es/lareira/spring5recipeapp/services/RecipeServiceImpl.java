package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;

  public RecipeServiceImpl(final RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Set<Recipe> getRecipes() {
    final Set<Recipe> recipeList = new HashSet<>();
    final Iterable<Recipe> iterableRecipes = this.recipeRepository.findAll();
    iterableRecipes.forEach(recipeList::add);
    return recipeList;
  }

  @Override
  public Recipe findRecipeById(Long id) {
    Optional<Recipe> recipe = recipeRepository.findById(id);

    return recipe.orElseThrow(() -> new RuntimeException("Recipe does not exist!"));
  }

}
