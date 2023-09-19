package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Province;
import me.kvdpxne.covilo.domain.persistence.IProvinceRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.IProvincePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.IProvinceJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class ProvinceDao implements IProvinceRepository {

  private final IProvinceJpaRepository repository;
  private final IProvincePersistenceMapper mapper;

  private Province toProvinceOrNull(final Optional<ProvinceEntity> source) {
    return source.map(this.mapper::toProvince).orElse(null);
  }

  @Override
  public Book<Province> findProvinces(final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.repository.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toProvince)
        .forEach(box::put)
    );
  }

  @Override
  public Book<Province> findProvincesByCountryIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.repository.findProvinceEntitiesByCountry_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toProvince)
        .forEach(box::put));
  }

  @Override
  public Province findProvinceByIdentifierOrNull(final UUID identifier) {
    final var entity = this.repository.findById(identifier);
    return toProvinceOrNull(entity);
  }

  @Override
  public Province findProvinceByNameOrNull(final String name) {
    return null;
  }
}
