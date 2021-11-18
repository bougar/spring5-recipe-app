package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.domain.UnitOfMeasure;
import es.lareira.spring5recipeapp.repositories.UnitOfMeasureRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public UnitOfMeasurementServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @Override
  public List<UnitOfMeasure> findAll() {
    List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
    Iterable<UnitOfMeasure> unitOfMeasureIterable = unitOfMeasureRepository.findAll();
    unitOfMeasureIterable.forEach(unitOfMeasureList::add);
    return unitOfMeasureList;
  }
}
