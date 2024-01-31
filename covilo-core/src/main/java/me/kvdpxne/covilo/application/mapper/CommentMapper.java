package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.application.dto.CommentDto;
import me.kvdpxne.covilo.domain.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    UserMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CommentMapper
  extends MapStructPresentationMapper<Comment, CommentDto> {}
