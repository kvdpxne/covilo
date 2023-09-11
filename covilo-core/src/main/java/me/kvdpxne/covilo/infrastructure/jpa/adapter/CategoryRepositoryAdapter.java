package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CategoryPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CategoryDao;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public final class CategoryRepositoryAdapter implements CategoryRepository {

  private final CategoryDao dao;
  private final CategoryPersistenceMapper mapper;

  @Override
  public Category findByCategoryOrNull(final UUID identifier) {
    return this.dao.findById(identifier).map(this.mapper::toCategory).orElse(null);
  }
}
