package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.infrastructure.jpa.PersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CategoryEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CategoryPersistenceMapper
  extends PersistenceMapper<Category, CategoryEntity> {}
