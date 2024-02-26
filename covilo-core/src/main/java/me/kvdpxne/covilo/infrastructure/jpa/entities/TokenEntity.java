package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.kvdpxne.covilo.domain.model.TokenType;

@Deprecated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "token")
public final class TokenEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false, updatable = false)
  private UUID identifier;

  @Column(name = "compact_token", unique = true, length = 512)
  private String compactToken;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TokenType tokenType;

  @Column(name = "revoked", nullable = false)
  private boolean revoked;

  @Column(name = "expired", nullable = false)
  private boolean expired;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "user_identifier")
  private UserEntity user;

}
