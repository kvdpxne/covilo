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

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false)
  private UUID identifier;

  @Column(name = "_email", unique = true)
  private String email;

  @Column(name = "_password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "_role")
  private Role role;


  @ToString.Exclude
  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<Token> tokens = new LinkedHashSet<>();

  @Column(name = "_firstname")
  private String firstname;

  @Column(name = "_lastname")
  private String lastname;

  @OneToMany(mappedBy = "reporter", orphanRemoval = true)
  private Collection<Crime> crimes = new ArrayList<>();

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_living_place_identifier")
  private City livingPlace;

  @Column(name = "_birth_date", nullable = false)
  private LocalDate birthDate;

}
