package es.lareira.spring5recipeapp.controllers;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

  private final RecipeService recipeService;

  public IngredientController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping("/recipe/{id}/ingredients")
  public String listIngredients(@PathVariable String id, Model model) {
    Recipe recipe = recipeService.findRecipeById(Long.valueOf(id));
    model.addAttribute("recipe", recipe);
    return "recipe/ingredients/list";
  }
}
