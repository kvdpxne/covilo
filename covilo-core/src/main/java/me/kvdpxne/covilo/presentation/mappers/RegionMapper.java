package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.presentation.dto.RegionDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper
public interface RegionMapper
  extends MapStructPresentationMapper<Region, RegionDto> {}
