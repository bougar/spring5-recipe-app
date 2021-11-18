package es.lareira.spring5recipeapp.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import es.lareira.spring5recipeapp.repositories.UnitOfMeasureRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasurementServiceImplTest {

  @Mock
  private UnitOfMeasureRepository unitOfMeasureRepository;

  private UnitOfMeasurementService unitOfMeasurementService;

  @BeforeEach
  void setUp() {
    this.unitOfMeasurementService = new UnitOfMeasurementServiceImpl(unitOfMeasureRepository);
  }

  @Test
  void findAll() {
    UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
    UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
    List<UnitOfMeasure> unitOfMeasureList = List.of(unitOfMeasure1, unitOfMeasure2);
    when(this.unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureList);
    List<UnitOfMeasure> actual = this.unitOfMeasurementService.findAll();
    verify(this.unitOfMeasureRepository).findAll();
    Assertions.assertEquals(2, actual.size());
    Assertions.assertEquals(unitOfMeasure1, actual.get(0));
    Assertions.assertEquals(unitOfMeasure2, actual.get(1));
  }
}