package es.lareira.spring5recipeapp.commands;

import es.lareira.spring5recipeapp.domain.Difficulty;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

  private Long id;

  @NotBlank
  @Size(min = 3, max = 255)
  private String description;

  @Min(1)
  @Max(999)
  private Integer preparationTime;

  @Min(1)
  @Max(999)
  private Integer cookingTime;

  @Min(1)
  @Max(100)
  private Integer servings;
  private String source;
 
  @URL
  private String url;

  @NotBlank
  private String directions;
  private Difficulty difficulty;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private NotesCommand notes;
  private Set<CategoryCommand> categories = new HashSet<>();
  private Byte[] image;
}
