package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Category;

public interface CategoryRepository {

  Category findByCategoryOrNull(final UUID identifier);
}
