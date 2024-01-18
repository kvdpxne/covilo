package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ClassificationEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ClassificationPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaClassificationRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class ClassificationDao implements ClassificationRepository {

  private final JpaClassificationRepository classificationDao;
  private final ClassificationPersistenceMapper classificationPersistenceMapper;

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
