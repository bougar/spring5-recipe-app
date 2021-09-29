package es.lareira.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import es.lareira.spring5recipeapp.commands.UnitOfMeasureCommand;
import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;

class UnitOfMeasureConverterTest {

  @Test
  void testToCommand() {
    UnitOfMeasureConverterImpl unitOfMeasureConverterImpl = new UnitOfMeasureConverterImpl();

    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(123L);
    unitOfMeasure.setDescription("The characteristics of someone or something");
    UnitOfMeasureCommand actualToCommandResult = unitOfMeasureConverterImpl.toCommand(
        unitOfMeasure);
    assertEquals("The characteristics of someone or something",
        actualToCommandResult.getDescription());
    assertEquals(123L, actualToCommandResult.getId().longValue());
  }

  @Test
  void testToDomain() {
    UnitOfMeasureConverterImpl unitOfMeasureConverterImpl = new UnitOfMeasureConverterImpl();

    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(123L);
    unitOfMeasureCommand.setDescription("The characteristics of someone or something");
    UnitOfMeasure actualToDomainResult = unitOfMeasureConverterImpl.toDomain(unitOfMeasureCommand);
    assertEquals("The characteristics of someone or something",
        actualToDomainResult.getDescription());
    assertEquals(123L, actualToDomainResult.getId().longValue());
  }
}

