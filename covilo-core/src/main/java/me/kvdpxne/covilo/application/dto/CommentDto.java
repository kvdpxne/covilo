package me.kvdpxne.covilo.application.dto;

import java.util.Collection;
import java.util.UUID;

public record CommentDto(
  UUID identifier,
  String content,
  UserDto author,
  Collection<CommentDto> replies
) {}
