package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.TokenDto;
import me.kvdpxne.covilo.domain.model.Token;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "TokenMapperImpl"
)
public interface ITokenMapper {

  TokenDto toTokenDto(final Token source);
}
