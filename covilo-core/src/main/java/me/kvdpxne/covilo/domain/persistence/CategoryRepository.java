package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Category;
import org.jetbrains.annotations.TestOnly;

public interface CategoryRepository {

  @TestOnly
  List<Category> getAll();

  Optional<Category> getByIdentifier(
    final UUID identifier
  );

  Optional<Category> getByName(
    final String name
  );

  void insertAll(
    final Collection<Category> categories
  );

  void insert(
    final Category category
  );
}
