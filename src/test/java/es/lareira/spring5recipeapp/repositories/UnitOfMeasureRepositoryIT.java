package es.lareira.spring5recipeapp.repositories;

import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UnitOfMeasureRepositoryIT {

  @Autowired
  private UnitOfMeasureRepository unitOfMeasureRepository;

  @Test
  @DirtiesContext
  void findByDescription() {
    Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
    Assertions.assertTrue(teaspoon.isPresent());
    Assertions.assertEquals("Teaspoon", teaspoon.get().getDescription());
  }

  @Test
  void findByDescriptionCup() {
    Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Cup");
    Assertions.assertTrue(teaspoon.isPresent());
    Assertions.assertEquals("Cup", teaspoon.get().getDescription());
  }
}