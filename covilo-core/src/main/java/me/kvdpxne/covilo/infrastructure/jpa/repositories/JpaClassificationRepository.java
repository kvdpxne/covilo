package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.ClassificationEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClassificationRepository
  extends JpaIdentifiableRepository<ClassificationEntity> {
}
