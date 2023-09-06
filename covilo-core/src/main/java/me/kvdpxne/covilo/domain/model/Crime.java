package me.kvdpxne.covilo.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "_crime")
public class Crime {

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
  private Set<Category> categories = new LinkedHashSet<>();

  @Column(name = "_time")
  private LocalDateTime time;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_reporter")
  private User reporter;

  @ToString.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "_place", nullable = false)
  private City place;

  @Column(name = "_confirmed", nullable = false)
  private boolean confirmed;

}
