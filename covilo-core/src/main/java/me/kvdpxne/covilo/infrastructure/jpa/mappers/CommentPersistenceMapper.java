package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Comment;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CommentPersistenceMapper
  extends MapStructPersistenceMapper<Comment, CommentEntity> {}
