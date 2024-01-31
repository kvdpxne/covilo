package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.AdministrativeDivisionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAdministrativeDivisionRepository
  extends JpaIdentifiableRepository<AdministrativeDivisionEntity> {


  @Query("select a from AdministrativeDivisionEntity a where upper(a.name) = upper(?1)")
  Optional<AdministrativeDivisionEntity> findByName(
    final String name
  );
}
