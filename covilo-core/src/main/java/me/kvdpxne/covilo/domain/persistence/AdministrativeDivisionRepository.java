package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import org.jetbrains.annotations.TestOnly;

public interface AdministrativeDivisionRepository {

  /**
   *
   */
  @TestOnly
  List<AdministrativeDivision> getAll();

  /**
   *
   */
  Book<AdministrativeDivision> getAll(
    final BookAttributes attributes
  );

  /**
   *
   */
  Optional<AdministrativeDivision> findByIdentifier(
    final UUID identifier
  );

  /**
   *
   */
  Optional<AdministrativeDivision> findByName(
    final String name
  );

  void insertAll(
    final Collection<AdministrativeDivision> administrativeDivisions
  );

  void insert(
    final AdministrativeDivision administrativeDivision
  );
}
