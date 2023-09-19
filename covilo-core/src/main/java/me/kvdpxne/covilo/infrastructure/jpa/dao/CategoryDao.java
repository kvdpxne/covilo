package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.persistence.ICategoryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ICategoryPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ICategoryJpaRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CategoryDao implements ICategoryRepository {

  private final ICategoryJpaRepository repository;
  private final ICategoryPersistenceMapper mapper;

  @Override
  public Collection<Category> findCategories() {
    return this.repository.findAll()
      .stream()
      .map(this.mapper::toCategory)
      .toList();
  }

  @Override
  public Category findCategoryByIdentifierOrNull(final UUID identifier) {
    return this.repository.findById(identifier)
      .map(this.mapper::toCategory)
      .orElse(null);
  }
}
