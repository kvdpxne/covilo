package me.kvdpxne.covilo.domain.persistence;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import me.kvdpxne.covilo.domain.model.Comment;

public interface CommentRepository {

  Stream<Comment> getAll();
  Stream<Comment> getAllByUserIdentifier(final UUID identifier);

  Optional<Comment> getByIdentifier(final UUID identifier);

  void insert(final Comment comment);

  void update(final Comment comment);

  void delete(final Comment comment);

  long count();
}
