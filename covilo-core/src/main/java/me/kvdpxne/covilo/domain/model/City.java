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

  @Column(name = "_name", nullable = false)
  private String name;

  @ToString.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "_province_identifier", nullable = false)
  private Province province;

  @OneToMany(mappedBy = "place", orphanRemoval = true)
  private List<Crime> crimes = new ArrayList<>();

  @OneToMany(mappedBy = "livingPlace", orphanRemoval = true)
  private Set<User> users = new LinkedHashSet<>();

}