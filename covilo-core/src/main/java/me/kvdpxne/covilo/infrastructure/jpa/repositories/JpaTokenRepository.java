package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.TokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface JpaTokenRepository
  extends JpaIdentifiableRepository<TokenEntity> {

  @Query(value = """
    SELECT t FROM TokenEntity t INNER JOIN UserEntity u\s
    ON t.user.identifier = u.identifier\s
    WHERE u.identifier = :identifier AND (t.expired = false OR t.revoked = false)\s
    """)
  Collection<TokenEntity> findAllValidTokenByUser(final UUID identifier);

  Optional<TokenEntity> findTokenByCompactToken(final String token);


  Optional<TokenEntity> findTokenByUser_Identifier(final UUID identifier);

  Collection<TokenEntity> findByUser_Identifier(final UUID identifier);
}
