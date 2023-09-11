package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.persistence.CityRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CityEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CityPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CityDao;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public final class CityRepositoryAdapter implements CityRepository {

  private final CityDao cityDao;
  private final CityPersistenceMapper cityPersistenceMapper;

  private City toCityOrNull(final Optional<CityEntity> source) {
    return source.map(this.cityPersistenceMapper::toCity).orElse(null);
  }

  @Override
  public City findByIdentifierOrNull(final UUID identifier) {
    final var entity = this.cityDao.findById(identifier);
    return this.toCityOrNull(entity);
  }
}
