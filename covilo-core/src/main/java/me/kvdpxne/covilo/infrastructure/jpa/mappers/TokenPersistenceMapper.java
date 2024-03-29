package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.infrastructure.jpa.entities.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Deprecated
@Mapper(
  uses = {
    UserPersistenceMapper.class
  }
)
public interface TokenPersistenceMapper {

  TokenEntity toDao(final Token source);

  Token toDomain(final TokenEntity source);
}
