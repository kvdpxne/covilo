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
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.kvdpxne.covilo.domain.model.TokenType;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "token")
@Table(name = "_token")
public class TokenEntity {

  @Builder.Default
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "_identifier", nullable = false, updatable = false)
  private UUID identifier = UUID.randomUUID();

  @Column(name = "_token", unique = true)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = "_token_type")
  private TokenType tokenType = TokenType.BEARER;

  @Column(name = "_revoked", nullable = false)
  private boolean revoked;

  @Column(name = "_expired", nullable = false)
  private boolean expired;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "_user_identifier")
  private UserEntity user;

}
