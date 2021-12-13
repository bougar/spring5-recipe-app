package es.lareira.spring5recipeapp.controllers;

import es.lareira.spring5recipeapp.commands.IngredientCommand;
import es.lareira.spring5recipeapp.commands.RecipeCommand;
import es.lareira.spring5recipeapp.commands.UnitOfMeasureCommand;
import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import es.lareira.spring5recipeapp.services.IngredientService;
import es.lareira.spring5recipeapp.services.RecipeService;
import es.lareira.spring5recipeapp.services.UnitOfMeasurementService;
import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

  private final RecipeService recipeService;

  private final IngredientService ingredientService;

  private final UnitOfMeasurementService unitOfMeasurementService;

  public IngredientController(RecipeService recipeService, IngredientService ingredientService,
      UnitOfMeasurementService unitOfMeasurementService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasurementService = unitOfMeasurementService;
  }

  @GetMapping("/recipe/{id}/ingredients")
  public String listIngredients(@PathVariable String id, Model model) {
    Recipe recipe = recipeService.findRecipeById(Long.valueOf(id));
    model.addAttribute("recipe", recipe);
    return "recipe/ingredients/list";
  }

  @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/update")
  public String updateRecipeIngredient(@PathVariable String recipeId,
      @PathVariable String ingredientId, Model model) {
    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(
        Long.valueOf(recipeId), Long.valueOf(ingredientId));
    List<UnitOfMeasure> unitOfMeasureList = unitOfMeasurementService.findAll();
    model.addAttribute("uomList", unitOfMeasureList);
    model.addAttribute("ingredient", ingredientCommand);
    return "recipe/ingredients/updateForm";
  }

  @GetMapping("/recipe/{recipeId}/ingredients/new")
  public String newRecipeIngredient(@PathVariable String recipeId, Model model) {
    RecipeCommand recipeCommandById = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
    assert recipeCommandById != null;
    List<UnitOfMeasure> unitOfMeasureList = unitOfMeasurementService.findAll();
    model.addAttribute("uomList", unitOfMeasureList);
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(Long.valueOf(recipeId));
    ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
    model.addAttribute("ingredient", ingredientCommand);
    return "recipe/ingredients/updateForm";
  }

  @PostMapping("/recipe/{recipeId}/ingredients")
  public String saveIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
    Long recipeId = ingredientCommand.getRecipeId();
    IngredientCommand savedIngredientCommand = this.ingredientService
        .saveIngredientCommand(ingredientCommand);
    return MessageFormat.format("redirect:/recipe/{0}/ingredients/{1}/show", recipeId,
        savedIngredientCommand.getId());
  }

  @RequestMapping({"/recipe/{recipeId}/ingredients/{ingredientId}/show",
      "/recipe/{recipeId}/ingredients/{ingredientId}"})
  public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId,
      Model model) {
    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(
        Long.valueOf(recipeId), Long.valueOf(ingredientId));
    model.addAttribute("ingredient", ingredientCommand);
    return "recipe/ingredients/show";
  }

  @PostMapping("/recipe/{recipeId}/ingredients/{ingredientId}/delete")
  public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
    this.ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
    return MessageFormat.format("redirect:/recipe/{0}/ingredients", recipeId);
  }
}
