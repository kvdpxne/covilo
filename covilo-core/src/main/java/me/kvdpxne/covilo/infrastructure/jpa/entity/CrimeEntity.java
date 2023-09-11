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

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "_crime")
public class CrimeEntity {

  @Builder.Default
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier = UUID.randomUUID();

  @Column(name = "_title")
  private String title;

  @Lob
  @Column(name = "_description")
  private String description;

  @ToString.Exclude
  @ManyToMany
  @JoinTable(name = "_crime_categories",
    joinColumns = @JoinColumn(name = "_crime"),
    inverseJoinColumns = @JoinColumn(name = "_categories"))
  private Set<CategoryEntity> categories = new LinkedHashSet<>();

  @Column(name = "_time")
  private LocalDateTime time;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_reporter")
  private UserEntity reporter;

  @ToString.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "_place", nullable = false)
  private CityEntity place;

  @Column(name = "_confirmed", nullable = false)
  private boolean confirmed;

}
