package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.domain.persistence.RegionRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ProvincePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaRegionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class ProvinceDao implements RegionRepository {

  private final JpaRegionRepository repository;
  private final ProvincePersistenceMapper mapper;

  private Region toProvinceOrNull(final Optional<ProvinceEntity> source) {
    return source.map(this.mapper::toProvince).orElse(null);
  }

  @Override
  public Book<Region> findProvinces(final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.repository.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toProvince)
        .forEach(box::put)
    );
  }

  @Override
  public Book<Region> findProvincesByCountryIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.repository.findProvinceEntitiesByCountry_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toProvince)
        .forEach(box::put));
  }

  @Override
  public Region findProvinceByIdentifierOrNull(final UUID identifier) {
    final var entity = this.repository.findById(identifier);
    return toProvinceOrNull(entity);
  }

  @Override
  public Region findProvinceByNameOrNull(final String name) {
    return null;
  }
}
