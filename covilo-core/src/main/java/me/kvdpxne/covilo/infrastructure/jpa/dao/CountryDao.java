package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.persistence.ICountryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ICountryPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ICountryJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CountryDao implements ICountryRepository {

  private final ICountryJpaRepository repository;
  private final ICountryPersistenceMapper mapper;

  private Country toCountryOrNull(final Optional<CountryEntity> source) {
    return source.map(this.mapper::toCountry).orElse(null);
  }

  @Override
  public Book<Country> findCountries(final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.repository.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCountry)
        .forEach(box::put)
    );
  }

  @Override
  public Country findCountryByIdentifierOrNull(final UUID identifier) {
    final var entity = this.repository.findById(identifier);
    return this.toCountryOrNull(entity);
  }

  @Override
  public Country findCountryByNameOrNull(final String name) {
    final var entity = this.repository.findByNameAllIgnoreCase(name);
    return this.toCountryOrNull(entity);
  }

  @Override
  public Country insertCountry(final Country country) {
    return this.mapper.toCountry(
      this.repository.save(
        this.mapper.toCountryEntity(country)
      )
    );
  }

  @Override
  public void deleteCountryByIdentifier(final UUID identifier) {
    this.repository.deleteById(identifier);
  }

  @Override
  public void deleteCountry(final Country country) {
    this.deleteCountryByIdentifier(country.identifier());
  }
}
