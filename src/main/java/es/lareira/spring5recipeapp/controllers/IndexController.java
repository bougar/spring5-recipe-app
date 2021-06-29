package es.lareira.spring5recipeapp.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.RecipeService;

@Controller
public class IndexController {
  private final RecipeService recipeService;

  public IndexController(final RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping({"", "/", "/index.html"})
  public String getIndexPage(final Model model) {
    final Set<Recipe> recipeSet = this.recipeService.getRecipes();
    model.addAttribute("recipes", recipeSet);
    return "index";
  }
}
