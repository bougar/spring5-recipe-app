package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.converters.RecipeConverter;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.exceptions.NotFoundException;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;

  private final RecipeConverter recipeConverter;

  public RecipeServiceImpl(final RecipeRepository recipeRepository,
      RecipeConverter recipeConverter) {
    this.recipeRepository = recipeRepository;
    this.recipeConverter = recipeConverter;
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
    return recipe.orElseThrow(
        () -> new NotFoundException("Recipe Not Found for Id value: " + id));
  }

  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(RecipeCommand command) {
    Recipe detachedRecipe = recipeConverter.toDomain(command);
    Recipe savedRecipe = recipeRepository.save(detachedRecipe);
    return recipeConverter.toCommand(savedRecipe);
  }

  @Override
  public RecipeCommand findRecipeCommandById(Long id) {
    Recipe recipeById = this.findRecipeById(id);
    return recipeConverter.toCommand(recipeById);
  }

  @Override
  public void deleteRecipeById(Long id) {
    recipeRepository.deleteById(id);
  }

}
