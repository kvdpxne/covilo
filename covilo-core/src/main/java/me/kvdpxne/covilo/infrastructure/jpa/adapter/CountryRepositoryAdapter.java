package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CountryPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CountryDao;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public final class CountryRepositoryAdapter implements CountryRepository {

  private final CountryDao countryDao;
  private final CountryPersistenceMapper countryPersistenceMapper;

  private Country toCountryOrNull(final Optional<CountryEntity> source) {
    return source.map(this.countryPersistenceMapper::toCountry).orElse(null);
  }

  @Override
  public Country findCountryByIdentifierOrNull(final UUID identifier) {
    final var entity = this.countryDao.findById(identifier);
    return this.toCountryOrNull(entity);
  }

  @Override
  public Country findCountryByNameOrNull(final String name) {
    final var entity = this.countryDao.findByNameAllIgnoreCase(name);
    return this.toCountryOrNull(entity);
  }
}
