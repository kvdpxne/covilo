package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Comment;
import me.kvdpxne.covilo.presentation.dto.CommentDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    UserMapper.class
  }
)
public interface CommentMapper
  extends MapStructPresentationMapper<Comment, CommentDto> {}
