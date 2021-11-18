package es.lareira.spring5recipeapp.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Builder
@ToString(exclude = {"recipe"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private BigDecimal amount;

  @OneToOne
  private UnitOfMeasure unitOfMeasure;

  @ManyToOne
  private Recipe recipe;

  public Ingredient(
      final String description,
      final BigDecimal amount,
      final UnitOfMeasure unitOfMeasure,
      final Recipe recipe) {
    this.description = description;
    this.amount = amount;
    this.unitOfMeasure = unitOfMeasure;
    this.recipe = recipe;
  }
}
