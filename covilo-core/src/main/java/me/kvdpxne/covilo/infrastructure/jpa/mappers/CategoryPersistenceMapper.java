package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CategoryEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryPersistenceMapper
  extends MapStructPersistenceMapper<Category, CategoryEntity> {}
