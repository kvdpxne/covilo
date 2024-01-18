package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
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

  private final JpaCityRepository repository;
  private final CityPersistenceMapper mapper;

  private City toCityOrNull(final Optional<CityEntity> source) {
    return source.map(this.mapper::toCity).orElse(null);
  }

  @Override
  public Book<City> findCities(final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.repository.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCity)
        .forEach(book::put)
    );
  }

  @Override
  public Book<City> findCitiesByProvinceIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.repository.findCityEntitiesByProvince_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCity)
        .forEach(book::put)
    );
  }

  @Override
  public City findByIdentifierOrNull(final UUID identifier) {
    final var entity = this.repository.findById(identifier);
    return this.toCityOrNull(entity);
  }
}
