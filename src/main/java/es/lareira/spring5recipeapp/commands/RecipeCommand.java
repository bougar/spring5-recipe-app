package es.lareira.spring5recipeapp.commands;

import es.lareira.spring5recipeapp.domain.Difficulty;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

  private Long id;
  private String description;
  private Integer preparationTime;
  private Integer cookingTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  private Difficulty difficulty;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private NotesCommand notes;
  private Set<CategoryCommand> categories = new HashSet<>();
  private Byte[] image;
}
