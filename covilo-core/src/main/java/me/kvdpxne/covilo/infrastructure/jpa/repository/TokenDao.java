package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.RepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.TokenEntity;
import org.springframework.data.jpa.repository.Query;

public interface TokenDao
  extends RepositoryViaIdentifier<TokenEntity> {

  @Query(value = """
    SELECT t FROM token t INNER JOIN user u\s
    ON t.user.identifier = u.identifier\s
    WHERE u.identifier = :identifier AND (t.expired = false OR t.revoked = false)\s
    """)
  Collection<TokenEntity> findAllValidTokenByUser(final UUID identifier);

  Optional<TokenEntity> findByToken(final String token);
}
