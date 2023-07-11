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
@Table(name = "_city")
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false)
  private UUID identifier;

  @Column(name = "_name")
  private String name;

  @Column(name = "_national_name", nullable = false, unique = true)
  private String nationalName;

  @ToString.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "_province", nullable = false)
  private Province province;

  @Enumerated(EnumType.STRING)
  @Column(name = "_capital")
  private CapitalType capital;

  @Column(name = "_population", nullable = false)
  private int population;

  @ToString.Exclude
  @OneToMany(mappedBy = "place", orphanRemoval = true)
  private List<Crime> crimes = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "livingPlace", orphanRemoval = true)
  private Set<User> users = new LinkedHashSet<>();
}