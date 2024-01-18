package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CategoryPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CategoryPersistenceMapperImpl"
)
public interface CategoryPersistenceMapper {

  CategoryEntity toCategoryEntity(final Category source);

  Category toCategory(final CategoryEntity source);
}
