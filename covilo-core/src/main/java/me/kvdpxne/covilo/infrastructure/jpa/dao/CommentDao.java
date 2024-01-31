package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Comment;
import me.kvdpxne.covilo.domain.persistence.CommentRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mappers.CommentPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repositories.JpaCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class CommentDao
  implements CommentRepository {

  private final JpaCommentRepository jpa;
  private final CommentPersistenceMapper mapper;

  @Override
  public Stream<Comment> getAll() {
    return this.jpa.findAll()
      .stream()
      .map(this.mapper::toDomain);
  }

  @Override
  public Stream<Comment> getAllByUserIdentifier(
    final UUID identifier
  ) {
    return null;
  }

  @Override
  public Optional<Comment> getByIdentifier(final UUID identifier) {
    return this.jpa.findById(identifier)
      .map(this.mapper::toDomain);
  }

  @Override
  public void insert(final Comment comment) {
    this.jpa.save(
      this.mapper.toDao(comment)
    );
  }

  @Override
  public void update(final Comment comment) {
    var toUpdate = this.jpa.getReferenceById(comment.identifier());
    toUpdate = this.mapper.partialUpdate(toUpdate, comment);
    this.jpa.save(toUpdate);
  }

  @Override
  public void delete(final Comment comment) {
    this.jpa.deleteById(comment.identifier());
  }

  @Override
  public long count() {
    return this.jpa.count();
  }
}
