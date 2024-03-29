package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.kvdpxne.covilo.domain.model.CapitalType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "city")
public final class CityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  @Column(name = "name")
  private String name;

  @Column(name = "national_name", nullable = false, unique = true)
  private String nationalName;

  @ToString.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "region_identifier", nullable = false)
  private RegionEntity region;

  @Enumerated(EnumType.STRING)
  @Column(name = "capital")
  private CapitalType capitalType;

  @Column(name = "population", nullable = false)
  private int population;

  @ToString.Exclude
  @OneToMany(mappedBy = "place", orphanRemoval = true)
  private List<CrimeEntity> crimes = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "livingPlace", orphanRemoval = true)
  private Set<UserEntity> users = new LinkedHashSet<>();
}