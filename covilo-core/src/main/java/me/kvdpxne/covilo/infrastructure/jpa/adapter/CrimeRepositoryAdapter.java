package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CrimePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CrimeDao;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public final class CrimeRepositoryAdapter implements CrimeRepository {

  private final CrimeDao dao;
  private final CrimePersistenceMapper mapper;

  @Override
  public Crime findCrimeByIdentifierOrNull(final UUID identifier) {
    return this.dao.findById(identifier).map(this.mapper::toCrime).orElse(null);
  }
}
