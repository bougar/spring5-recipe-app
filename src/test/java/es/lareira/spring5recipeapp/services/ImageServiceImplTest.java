package es.lareira.spring5recipeapp.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.repositories.RecipeRepository;
import java.util.Optional;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

  @Mock
  private RecipeRepository recipeRepository;

  private ImageService imageService;

  @BeforeEach
  void setUp() {
    imageService = new ImageServiceImpl(recipeRepository);
  }

  @SneakyThrows
  @ParameterizedTest
  @CsvSource({"1, content1", "44, myContent2", "934, random"})
  void saveImage(Long recipeId, String fileContent) {
    MultipartFile mockFile = new MockMultipartFile("file", fileContent.getBytes());
    Recipe recipe = Recipe.builder()
        .id(recipeId)
        .build();
    when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
    imageService.saveImage(recipeId, mockFile);
    mockFile.getBytes();
    Byte[] expectedBytes = ArrayUtils.toObject(mockFile.getBytes());
    ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
    verify(recipeRepository, times(1))
        .save(recipeArgumentCaptor.capture());
    Assertions.assertArrayEquals(expectedBytes, recipeArgumentCaptor.getValue().getImage());
  }
}