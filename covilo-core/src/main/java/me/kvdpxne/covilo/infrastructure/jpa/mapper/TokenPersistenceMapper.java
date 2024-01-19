package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.infrastructure.jpa.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Deprecated
@Mapper(
  uses = {
    UserPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface TokenPersistenceMapper {

  TokenEntity toDao(final Token source);

  Token toDomain(final TokenEntity source);
}
