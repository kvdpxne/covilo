package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.domain.persistence.RegionRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.RegionEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.RegionPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaRegionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class RegionDao implements RegionRepository {

  private final JpaRegionRepository jpa;
  private final RegionPersistenceMapper mapper;

  private Region toProvinceOrNull(final Optional<RegionEntity> source) {
    return source.map(this.mapper::toDomain).orElse(null);
  }

  @Override
  public Book<Region> findProvinces(final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.jpa.findAll(
          0 >= attributes.size()
            ? Pageable.unpaged()
            : PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toDomain)
        .forEach(box::put)
    );
  }

  @Override
  public Book<Region> findProvincesByCountryIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, box ->
      this.jpa.findProvinceEntitiesByCountry_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toDomain)
        .forEach(box::put));
  }

  @Override
  public Region findProvinceByIdentifierOrNull(final UUID identifier) {
    final var entity = this.jpa.findById(identifier);
    return toProvinceOrNull(entity);
  }

  @Override
  public Region findProvinceByNameOrNull(final String name) {
    return null;
  }
}
