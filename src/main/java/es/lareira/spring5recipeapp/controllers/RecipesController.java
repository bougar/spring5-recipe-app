package es.lareira.spring5recipeapp.controllers;

import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipesController {

  private final RecipeService recipeService;

  public RecipesController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping("/recipe/show/{id}")
  public String showRecipe(@PathVariable Long id, Model model) {
    Recipe recipe = recipeService.findRecipeById(id);

    model.addAttribute("recipe", recipe);

    return "recipe/show";
  }

  @RequestMapping("/recipe/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }

  @PostMapping("recipe")
  public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
    RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
    return "redirect:/recipe/show/" + saveRecipeCommand.getId();
  }

}
