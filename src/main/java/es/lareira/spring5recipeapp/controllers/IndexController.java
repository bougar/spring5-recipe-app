package es.lareira.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.lareira.spring5recipeapp.repositories.CategoryRepository;
import es.lareira.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController {

  public IndexController(
      final CategoryRepository categoryRepository,
      final UnitOfMeasureRepository unitOfMeasureRepository) {}

  @RequestMapping({"", "/", "/index.html"})
  public static String getIndexPage() {
    return "index";
  }
}
