package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Comment;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CommentEntity;
import me.kvdpxne.covilo.presentation.mappers.UserMapper;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    UserMapper.class
  }
)
public interface CommentPersistenceMapper
  extends MapStructPersistenceMapper<Comment, CommentEntity> {}
