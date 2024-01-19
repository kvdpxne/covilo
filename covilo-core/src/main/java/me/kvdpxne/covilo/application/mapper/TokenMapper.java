package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.TokenDto;
import me.kvdpxne.covilo.domain.model.Token;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Deprecated
@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "TokenMapperImpl"
)
public interface TokenMapper {

  TokenDto toTokenDto(final Token source);
}
