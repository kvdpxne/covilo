package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.persistence.CityRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CityEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CityPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaCityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CityDao implements CityRepository {

  private final JpaCityRepository jpa;
  private final CityPersistenceMapper mapper;

  private City toCityOrNull(final Optional<CityEntity> source) {
    return source.map(this.mapper::toDomain).orElse(null);
  }

  @Override
  public Book<City> findCities(final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.jpa.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toDomain)
        .forEach(book::put)
    );
  }

  @Override
  public Book<City> findCitiesByProvinceIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.jpa.findCityEntitiesByRegion_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toDomain)
        .forEach(book::put)
    );
  }

  @Override
  public City findByIdentifierOrNull(final UUID identifier) {
    final var entity = this.jpa.findById(identifier);
    return this.toCityOrNull(entity);
  }
}
