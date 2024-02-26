package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.presentation.dto.TokenDto;
import org.mapstruct.Mapper;

@Deprecated
@Mapper
public interface TokenMapper {

  TokenDto toTokenDto(final Token source);
}
