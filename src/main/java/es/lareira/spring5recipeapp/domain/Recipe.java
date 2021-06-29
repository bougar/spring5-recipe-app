package es.lareira.spring5recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private Integer preparationTime;
  private Integer cookingTime;
  private String source;
  private String url;
  @Lob private String directions;

  @Lob private Byte[] image;

  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private Set<Ingredient> ingredients = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL)
  private Notes notes;

  @ManyToMany
  @JoinTable(
      name = "recipe_category",
      joinColumns = @JoinColumn(name = "recipe_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Integer getPreparationTime() {
    return this.preparationTime;
  }

  public void setPreparationTime(final Integer preparationTime) {
    this.preparationTime = preparationTime;
  }

  public Integer getCookingTime() {
    return this.cookingTime;
  }

  public void setCookingTime(final Integer cookingTime) {
    this.cookingTime = cookingTime;
  }

  public String getSource() {
    return this.source;
  }

  public void setSource(final String source) {
    this.source = source;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getDirections() {
    return this.directions;
  }

  public void setDirections(final String directions) {
    this.directions = directions;
  }

  public Byte[] getImage() {
    return this.image;
  }

  public void setImage(final Byte[] image) {
    this.image = image;
  }

  public Notes getNotes() {
    return this.notes;
  }

  public void setNotes(final Notes notes) {
    this.notes = notes;
  }

  public Difficulty getDifficulty() {
    return this.difficulty;
  }

  public void setDifficulty(final Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public Set<Ingredient> getIngredients() {
    return this.ingredients;
  }

  public void setIngredients(final Set<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public Set<Category> getCategories() {
    return this.categories;
  }

  public void setCategories(final Set<Category> categories) {
    this.categories = categories;
  }
}
