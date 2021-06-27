package es.lareira.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import es.lareira.spring5recipeapp.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {}
