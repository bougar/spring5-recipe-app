package es.lareira.spring5recipeapp.converters;

import es.lareira.spring5recipeapp.commands.CategoryCommand;
import es.lareira.spring5recipeapp.domain.Category;
import es.lareira.spring5recipeapp.domain.Recipe;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryConverterTest {

  private CategoryConverter categoryConverter;

  @BeforeEach
  void setUp() {
    categoryConverter = new CategoryConverterImpl();
  }

  @Test
  void toCommand() {
    Set<Recipe> recipeSet = Set.of(Recipe.builder().id(1L).build(),
        Recipe.builder().id(2L).build());
    Category category = Category.builder()
        .id(1L)
        .recipes(recipeSet)
        .description("description")
        .build();
    CategoryCommand categoryCommand = categoryConverter.toCommand(category);
    Assertions.assertEquals(1L, categoryCommand.getId());
    Assertions.assertEquals("description", categoryCommand.getDescription());
  }

  @Test
  void toCommandWhenEntityIsNull() {
    CategoryCommand categoryCommand = categoryConverter.toCommand(null);
    Assertions.assertNull(categoryCommand);
  }

  @Test
  void toDomain() {
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setDescription("description");
    categoryCommand.setId(1L);
    Category category = categoryConverter.toDomain(categoryCommand);
    Assertions.assertEquals("description", category.getDescription());
    Assertions.assertEquals(1L, category.getId());
    Assertions.assertNull(category.getRecipes());
  }

  @Test
  void toDomainWhenCommandIsNull() {
    Category category = categoryConverter.toDomain(null);
    Assertions.assertNull(category);
  }
}