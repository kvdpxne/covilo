package me.kvdpxne.covilo.infrastructure.jpa.repository;

import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryJpaRepository
  extends JpaRepositoryViaIdentifier<CategoryEntity> {
}