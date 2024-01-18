package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.infrastructure.jpa.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    UserPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "TokenPersistenceMapperImpl"
)
public interface TokenPersistenceMapper {

  TokenEntity toTokenEntity(final Token source);

  Token toToken(final TokenEntity source);
}
