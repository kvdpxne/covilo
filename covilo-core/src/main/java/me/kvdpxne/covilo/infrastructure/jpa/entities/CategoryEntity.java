package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a category entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public final class CategoryEntity {

  /**
   * The unique identifier for the category entity.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  /**
   * The name of the category.
   */
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  /**
   * The set of classifications associated with this category.
   */
  @ToString.Exclude
  @ManyToMany
  @JoinTable(
    name = "category_classifications",
    joinColumns = @JoinColumn(
      name = "category_identifier"
    ),
    inverseJoinColumns = @JoinColumn(
      name = "classification_identifier"
    )
  )
  private Set<ClassificationEntity> classifications = new LinkedHashSet<>();
}