package es.lareira.spring5recipeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Spring5RecipeAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(Spring5RecipeAppApplication.class, args);
  }

}
