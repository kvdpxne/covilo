package me.kvdpxne.covilo.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "province")
public final class ProvinceEntity {

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
  @JoinColumn(name = "country", nullable = false)
  private CountryEntity country;

  @ToString.Exclude
  @OneToMany(mappedBy = "province", orphanRemoval = true)
  private Set<CityEntity> cities = new LinkedHashSet<>();

}