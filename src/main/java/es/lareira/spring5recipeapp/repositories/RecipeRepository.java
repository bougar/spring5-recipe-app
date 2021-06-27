package es.lareira.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import es.lareira.spring5recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {}
