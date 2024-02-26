package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a crime entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "crime")
public final class CrimeEntity {

  /**
   * The unique identifier for the crime entity.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  /**
   * The time when the crime occurred.
   */
  @Column(name = "time")
  private LocalDateTime time;

  /**
   * The city where the crime occurred.
   */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "place_identifier", nullable = false)
  private CityEntity place;

  /**
   * The classification of the crime.
   */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "classification_identifier")
  private ClassificationEntity classification;

  /**
   * The categories associated with the crime.
   */
  @ToString.Exclude
  @ManyToMany
  @JoinTable(
    name = "crime_categories",
    joinColumns = @JoinColumn(
      name = "crime_identifier"
    ),
    inverseJoinColumns = @JoinColumn(
      name = "category_identifier"
    )
  )
  private Set<CategoryEntity> categories = new LinkedHashSet<>();

  /**
   * The user who reported the crime.
   */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_identifier")
  private UserEntity reporter;

  /**
   * The detailed information associated with the crime.
   */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "crime_details_identifier")
  private CrimeDetailsEntity crimeDetails;

  /**
   * Indicates whether the crime has been confirmed.
   */
  @Column(name = "confirmed", nullable = false)
  private boolean confirmed;
}
