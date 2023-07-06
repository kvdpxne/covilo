package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@Table(name = "_province")
public class Province {

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
  @JoinColumn(name = "_country", nullable = false)
  private Country country;

  @ToString.Exclude
  @OneToMany(mappedBy = "province", orphanRemoval = true)
  private Set<City> cities = new LinkedHashSet<>();

}