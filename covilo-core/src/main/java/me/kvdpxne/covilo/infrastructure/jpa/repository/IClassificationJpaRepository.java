package me.kvdpxne.covilo.infrastructure.jpa.repository;

import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ClassificationEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassificationJpaRepository
  extends JpaRepositoryViaIdentifier<ClassificationEntity> {
}
