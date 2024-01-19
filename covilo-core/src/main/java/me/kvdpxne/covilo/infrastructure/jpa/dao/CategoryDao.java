package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CategoryPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaCategoryRepository;
import me.kvdpxne.covilo.shared.Validation;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CategoryDao
  implements CategoryRepository {

  private final JpaCategoryRepository jpa;
  private final CategoryPersistenceMapper mapper;

  @Override
  public List<Category> getAll() {
    return this.jpa.findAll()
      .stream()
      .map(this.mapper::toDomain)
      .toList();
  }

  @Override
  public Optional<Category> getByIdentifier(
    final UUID identifier
  ) {
    Validation.check(identifier);
    return this.jpa.findById(identifier)
      .map(this.mapper::toDomain);
  }

  @Override
  public Optional<Category> getByName(
    final String name
  ) {
    Validation.empty(name);
    return this.jpa.findByName(name)
      .map(this.mapper::toDomain);
  }

  @Override
  public void insertAll(
    final Collection<Category> categories
  ) {
    Validation.empty(categories);
    categories.stream()
      .map(this.mapper::toDao)
      .forEach(this.jpa::save);
  }

  @Override
  public void insert(
    final Category category
  ) {
    this.insertAll(
      List.of(category)
    );
  }
}
