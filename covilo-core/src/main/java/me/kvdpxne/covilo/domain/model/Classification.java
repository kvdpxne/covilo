package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "_classification")
public class Classification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier;

  @Column(name = "_name", nullable = false, unique = true)
  private String name;

  @ToString.Exclude
  @OneToMany(mappedBy = "classification", orphanRemoval = true)
  private Set<Category> categories = new LinkedHashSet<>();
}