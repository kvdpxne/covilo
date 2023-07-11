package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier;

  @Column(name = "_name", nullable = false, unique = true)
  private String name;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_classification")
  private Classification classification;

  @ToString.Exclude
  @ManyToMany(mappedBy = "categories")
  private Set<Crime> crimes = new LinkedHashSet<>();
}