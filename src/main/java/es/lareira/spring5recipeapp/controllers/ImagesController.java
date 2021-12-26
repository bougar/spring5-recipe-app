package es.lareira.spring5recipeapp.controllers;

import es.lareira.spring5recipeapp.domain.Recipe;
import es.lareira.spring5recipeapp.services.ImageService;
import es.lareira.spring5recipeapp.services.RecipeService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImagesController {

  private final RecipeService recipeService;

  private final ImageService imageService;

  public ImagesController(RecipeService recipeService,
      ImageService imageService) {
    this.recipeService = recipeService;
    this.imageService = imageService;
  }

  @GetMapping("recipe/{recipeId}/image")
  public String getUploadImageForm(Model model, @PathVariable String recipeId) {
    model.addAttribute("recipeId", recipeId);
    return "recipe/imageuploadform";
  }

  @PostMapping("recipe/{recipeId}/image")
  public String uploadImage(@PathVariable String recipeId, @RequestParam MultipartFile file) {
    imageService.saveImage(Long.valueOf(recipeId), file);
    return String.format("redirect:/recipe/%s/show", recipeId);
  }

  @SneakyThrows
  @GetMapping("recipe/{recipeId}/recipeimage")
  public void renderImage(@PathVariable String recipeId, HttpServletResponse response) {
    Recipe recipe = recipeService.findRecipeById(Long.valueOf(recipeId));

    Byte[] image = recipe.getImage();

    byte[] primitiveArrayImage = ArrayUtils.toPrimitive(image);

    response.setContentType("image/jpeg");
    InputStream inputStream = new ByteArrayInputStream(primitiveArrayImage);
    IOUtils.copy(inputStream, response.getOutputStream());
  }

}
