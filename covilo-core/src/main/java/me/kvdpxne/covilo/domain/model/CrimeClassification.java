package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "crime_classification")
@Table(name = "_crime_classification")
public class CrimeClassification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false)
  private UUID identifier;

  @Column(name = "_name", nullable = false, unique = true)
  private String name;

  @ToString.Exclude
  @ManyToMany(mappedBy = "classifications")
  private Set<Crime> crimes = new LinkedHashSet<>();

}
