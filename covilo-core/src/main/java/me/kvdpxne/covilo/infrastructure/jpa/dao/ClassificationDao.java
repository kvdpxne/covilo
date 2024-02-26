package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.ClassificationEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mappers.ClassificationPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repositories.JpaClassificationRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class ClassificationDao implements ClassificationRepository {

  private final JpaClassificationRepository jpa;
  private final ClassificationPersistenceMapper mapper;

  private Classification toClassificationOrNull(final Optional<ClassificationEntity> source) {
    return source.map(this.mapper::toDomain).orElse(null);
  }

  @Override
  public Stream<Classification> getAll() {
    return this.jpa.findAll()
      .stream()
      .map(this.mapper::toDomain);
  }

  @Override
  public Optional<Classification> findClassificationByIdentifier(
    final UUID identifier
  ) {
    return this.jpa.findById(identifier)
      .map(this.mapper::toDomain);
  }

  @Override
  public Classification findClassificationByIdentifierOrNull(final UUID identifier) {
    final var entity = this.jpa.findById(identifier);
    return this.toClassificationOrNull(entity);
  }

  @Override
  public Classification findClassificationByNameOrNull(final String name) {
    return null;
  }
}
