package es.lareira.spring5recipeapp.controllers;

import es.lareira.spring5recipeapp.services.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImagesController {

  private final ImageService imageService;

  public ImagesController(ImageService imageService) {
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

}
