package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CountryEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mappers.CountryPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repositories.JpaCountryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CountryDao implements CountryRepository {

  private final JpaCountryRepository jpa;
  private final CountryPersistenceMapper mapper;

  private Country toCountryOrNull(final Optional<CountryEntity> source) {
    return source.map(this.mapper::toDomain).orElse(null);
  }

  @Override
  public List<Country> getAll() {
    return this.jpa.findAll()
      .stream()
      .map(this.mapper::toDomain)
      .toList();
  }

  @Override
  public Book<Country> getAll(final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.jpa.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toDomain)
        .forEach(box::put)
    );
  }

  @Override
  public Country findCountryByIdentifierOrNull(final UUID identifier) {
    final var entity = this.jpa.findById(identifier);
    return this.toCountryOrNull(entity);
  }

  @Override
  public Country findCountryByNameOrNull(final String name) {
    final var entity = this.jpa.findByNameAllIgnoreCase(name);
    return this.toCountryOrNull(entity);
  }

  @Override
  public Country insertCountry(final Country country) {
    return this.mapper.toDomain(
      this.jpa.save(
        this.mapper.toDao(country)
      )
    );
  }

  @Override
  public void deleteCountryByIdentifier(final UUID identifier) {
    this.jpa.deleteById(identifier);
  }

  @Override
  public void deleteCountry(final Country country) {
    this.deleteCountryByIdentifier(country.identifier());
  }
}
