package es.lareira.spring5recipeapp.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import es.lareira.spring5recipeapp.services.ImageService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ImagesControllerTest {

  @Mock
  private ImageService imageService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    ImagesController imagesController = new ImagesController(imageService);
    mockMvc = MockMvcBuilders.standaloneSetup(imagesController).build();
  }

  @SneakyThrows
  @ParameterizedTest
  @ValueSource(strings = {"5", "8", "17"})
  void getImageForm(String recipeId) {
    String path = String.format("/recipe/%s/image", recipeId);
    mockMvc.perform(MockMvcRequestBuilders.get(path))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attribute("recipeId", recipeId))
        .andExpect(MockMvcResultMatchers.view().name("recipe/imageuploadform"));
  }

  @SneakyThrows
  @ParameterizedTest
  @ValueSource(strings = {"5", "8", "17"})
  void uploadImage(String recipeId) {
    String path = String.format("/recipe/%s/image", recipeId);
    MockMultipartFile mockFile = new MockMultipartFile("file", "mockFile.txt", "text/plain",
        "Linux is the best OS".getBytes());
    String expectLocationHeader = String.format("/recipe/%s/show", recipeId);
    mockMvc.perform(MockMvcRequestBuilders.multipart(path).file(mockFile))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.header().string("Location", expectLocationHeader));

    verify(imageService, times(1))
        .saveImage(eq(Long.valueOf(recipeId)), eq(mockFile));
  }

}
