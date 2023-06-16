package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

  @Query(value = """
    SELECT t FROM token t INNER JOIN user u\s
    ON t.user.identifier = u.identifier\s
    WHERE u.identifier = :identifier AND (t.expired = false OR t.revoked = false)\s
    """)
  List<Token> findAllValidTokenByUser(final UUID identifier);

  Optional<Token> findByToken(final String token);
}
