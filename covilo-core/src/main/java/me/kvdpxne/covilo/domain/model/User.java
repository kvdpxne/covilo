package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "user")
@Table(name = "_user")
public class User {

  @Builder.Default
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false)
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
  private City livingPlace;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<Token> tokens = new LinkedHashSet<>();

  @OneToMany(mappedBy = "reporter", orphanRemoval = true)
  private Collection<Crime> crimes = new ArrayList<>();
}
