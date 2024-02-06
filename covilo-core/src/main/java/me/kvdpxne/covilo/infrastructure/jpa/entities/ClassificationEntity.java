package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a classification entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "classification")
public final class ClassificationEntity {

  /**
   * The unique identifier for the classification entity.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  /**
   * The name of the classification.
   */
  @Column(name = "name", nullable = false, unique = true)
  private String name;
}