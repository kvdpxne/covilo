package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents detailed information associated with a crime.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "crime_details")
public class CrimeDetailsEntity {

  /**
   * The unique identifier for the crime details.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  /**
   * A description of the crime details.
   */
  @Column(name = "description", length = 256)
  private String description;

  /**
   * The comments associated with the crime details.
   */
  @ToString.Exclude
  @ManyToMany
  @JoinTable(
    name = "crime_details_comments",
    joinColumns = @JoinColumn(
      name = "crime_details_identifier"
    ),
    inverseJoinColumns = @JoinColumn(
      name = "comment_identifier"
    )
  )
  private Set<CommentEntity> comments = new LinkedHashSet<>();

}
