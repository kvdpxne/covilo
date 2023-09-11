package me.kvdpxne.covilo.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "_category")
public class CategoryEntity {

  @Builder.Default
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier = UUID.randomUUID();

  @Column(name = "_name", nullable = false, unique = true)
  private String name;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_classification")
  private ClassificationEntity classification;

  @ToString.Exclude
  @ManyToMany(mappedBy = "categories")
  private Set<CrimeEntity> crimes = new LinkedHashSet<>();
}