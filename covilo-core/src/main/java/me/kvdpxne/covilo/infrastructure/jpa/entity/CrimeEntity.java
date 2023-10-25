package me.kvdpxne.covilo.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "crime")
public final class CrimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  @Column(name = "title")
  private String title;

  @Column(name = "description", length = 256)
  private String description;

  @ToString.Exclude
  @ManyToMany
  @JoinTable(name = "crime_categories",
    joinColumns = @JoinColumn(name = "crime_identifier"),
    inverseJoinColumns = @JoinColumn(name = "category_identifier"))
  private Set<CategoryEntity> categories = new LinkedHashSet<>();

  @Column(name = "time")
  private LocalDateTime time;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "reporter")
  private UserEntity reporter;

  @ToString.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "place", nullable = false)
  private CityEntity place;

  @Column(name = "confirmed", nullable = false)
  private boolean confirmed;

}
