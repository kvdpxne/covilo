package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.presentation.dto.CategoryDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper
  extends MapStructPresentationMapper<Category, CategoryDto> {}
