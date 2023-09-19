package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.persistence.IClassificationRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ClassificationEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.IClassificationPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.IClassificationJpaRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class ClassificationDao implements IClassificationRepository {

  private final IClassificationJpaRepository classificationDao;
  private final IClassificationPersistenceMapper classificationPersistenceMapper;

  private Classification toClassificationOrNull(final Optional<ClassificationEntity> source) {
    return source.map(this.classificationPersistenceMapper::toClassification).orElse(null);
  }

  @Override
  public Classification findClassificationByIdentifierOrNull(final UUID identifier) {
    final var entity = this.classificationDao.findById(identifier);
    return this.toClassificationOrNull(entity);
  }

  @Override
  public Classification findClassificationByNameOrNull(final String name) {
    return null;
  }
}
