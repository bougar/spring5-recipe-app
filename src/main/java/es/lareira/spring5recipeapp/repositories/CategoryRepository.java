package es.lareira.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import es.lareira.spring5recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {}
