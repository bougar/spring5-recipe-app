package es.lareira.spring5recipeapp.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private BigDecimal amount;

  @OneToOne private UnitOfMeasure unitOfMeasure;

  @ManyToOne private Recipe recipe;

  public Ingredient(
      final String description, final BigDecimal amount, final UnitOfMeasure unitOfMeasure, final Recipe recipe) {
    this.description = description;
    this.amount = amount;
    this.unitOfMeasure = unitOfMeasure;
    this.recipe = recipe;
  }

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

  public BigDecimal getAmount() {
    return this.amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  public UnitOfMeasure getUnitOfMeasure() {
    return this.unitOfMeasure;
  }

  public void setUnitOfMeasure(final UnitOfMeasure unitOfMeasure) {
    this.unitOfMeasure = unitOfMeasure;
  }

  public Recipe getRecipe() {
    return this.recipe;
  }

  public void setRecipe(final Recipe recipe) {
    this.recipe = recipe;
  }
}
