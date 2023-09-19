package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Category;

public interface ICategoryRepository {

  Collection<Category> findCategories();

  Category findCategoryByIdentifierOrNull(final UUID identifier);
}
