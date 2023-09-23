package me.kvdpxne.covilo.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.kvdpxne.covilo.domain.model.AdministrativeDivisionType;
import me.kvdpxne.covilo.domain.model.Continent;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "country")
public final class CountryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "administrative_division")
  private AdministrativeDivisionType administrativeDivision;

  @Enumerated(EnumType.STRING)
  @Column(name = "continent", nullable = false)
  private Continent continent;

  @ToString.Exclude
  @OneToMany(mappedBy = "country", orphanRemoval = true)
  private Set<ProvinceEntity> provinces = new LinkedHashSet<>();
}
