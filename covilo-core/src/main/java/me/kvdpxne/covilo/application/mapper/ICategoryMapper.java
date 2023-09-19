package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.CategoryDto;
import me.kvdpxne.covilo.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CategoryMapperImpl"
)
public interface ICategoryMapper {

  CategoryDto toCategoryDto(final Category source);

  Category toCategory(final CategoryDto source);
}
