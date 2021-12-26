package es.lareira.spring5recipeapp.services;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

  private final RecipeRepository recipeRepository;

  public ImageServiceImpl(
      RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }


  @SneakyThrows
  @Override
  public void saveImage(Long recipeId, MultipartFile multipartFile) {
    Recipe recipe = recipeRepository.findById(recipeId)
        .orElseThrow(() -> new RuntimeException("RECIPE NOT FOUND!!!"));
    Byte[] imageBytes = ArrayUtils.toObject(multipartFile.getBytes());
    recipe.setImage(imageBytes);
    recipeRepository.save(recipe);
  }
}
