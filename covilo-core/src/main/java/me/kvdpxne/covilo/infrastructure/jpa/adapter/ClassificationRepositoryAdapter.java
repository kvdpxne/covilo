package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ClassificationEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ClassificationPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ClassificationDao;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public final class ClassificationRepositoryAdapter implements ClassificationRepository {

  private final ClassificationDao classificationDao;
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
