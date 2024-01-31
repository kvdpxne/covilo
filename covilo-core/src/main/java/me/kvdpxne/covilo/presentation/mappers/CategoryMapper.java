package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.presentation.dto.CategoryDto;
import me.kvdpxne.covilo.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CategoryMapper
  extends MapStructPresentationMapper<Category, CategoryDto> {}
