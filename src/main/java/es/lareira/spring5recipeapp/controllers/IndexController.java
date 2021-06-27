package es.lareira.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @RequestMapping({"", "/", "/index.html"})
  public static String getIndexPage() {
    return "index";
  }
}
