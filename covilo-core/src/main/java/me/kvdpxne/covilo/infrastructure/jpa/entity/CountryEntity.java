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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.kvdpxne.covilo.domain.model.AdministrativeDivisionType;
import me.kvdpxne.covilo.domain.model.Continent;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "_country")
public class CountryEntity {

  @Builder.Default
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier = UUID.randomUUID();

  @Column(name = "_name", nullable = false, unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "administrative_division_type")
  private AdministrativeDivisionType administrativeDivisionType;

  @Enumerated(EnumType.STRING)
  @Column(name = "_continent", nullable = false)
  private Continent continent;

  @ToString.Exclude
  @OneToMany(mappedBy = "country", orphanRemoval = true)
  private Set<ProvinceEntity> provinces = new LinkedHashSet<>();
}
