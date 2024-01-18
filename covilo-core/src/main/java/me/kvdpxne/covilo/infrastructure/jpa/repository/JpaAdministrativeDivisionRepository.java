package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.AdministrativeDivisionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAdministrativeDivisionRepository
  extends JpaRepositoryViaIdentifier<AdministrativeDivisionEntity> {


  @Query("select a from AdministrativeDivisionEntity a where upper(a.name) = upper(?1)")
  Optional<AdministrativeDivisionEntity> findByName(
    final String name
  );
}
