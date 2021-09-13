package es.lareira.spring5recipeapp.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

  private Category category;

  @BeforeEach
  void setUp() {
    this.category = new Category();
  }

  @Test
  void getId() {
    this.category.setId(4L);
    Assertions.assertEquals(4L, this.category.getId());
  }

  @Test
  void getDescription() {}

  @Test
  void getRecipes() {}
}
