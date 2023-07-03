package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "crime")
@Table(name = "_crime")
public class Crime {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false)
  private UUID identifier;

  @Lob
  @Column(name = "_description")
  private String description;

  @Column(name = "_confirmed", nullable = false)
  private boolean confirmed;

  @ToString.Exclude
  @ManyToMany
  @JoinTable(
    name = "_crime_by_crime_classifications",
    joinColumns = @JoinColumn(
      name = "_crime_identifier"
    ),
    inverseJoinColumns = @JoinColumn(
      name = "_crime_classification_identifier"
    )
  )
  private Set<CrimeClassification> classifications = new LinkedHashSet<>();

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_reporter_identifier")
  private User reporter;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_place_identifier")
  private City place;

}
