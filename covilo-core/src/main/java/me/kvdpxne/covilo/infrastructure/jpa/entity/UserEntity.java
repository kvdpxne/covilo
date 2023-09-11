package me.kvdpxne.covilo.infrastructure.jpa.entity;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.Role;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "user")
@Table(name = "_user")
public class UserEntity {

  @Builder.Default
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier = UUID.randomUUID();

  @Column(name = "_email", unique = true)
  private String email;

  @Column(name = "_password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "_role")
  private Role role;

  @Column(name = "_first_name")
  private String firstName;

  @Column(name = "_last_name")
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "_gender")
  private Gender gender;

  @Column(name = "_birth_date", nullable = false)
  private LocalDate birthDate;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_living_place")
  private CityEntity livingPlace;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<TokenEntity> tokens = new LinkedHashSet<>();

  @OneToMany(mappedBy = "reporter", orphanRemoval = true)
  private Collection<CrimeEntity> crimes = new ArrayList<>();
}
