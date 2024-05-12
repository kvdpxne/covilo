package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;
import me.kvdpxne.covilo.domain.aggregation.Auditable;

@Builder(toBuilder = true)
public record Comment(
  UUID identifier,
  String content,
  User author,
  Collection<Comment> replies,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) implements Auditable {

  /**
   *
   */
  public boolean hasReplies() {
    return null != this.replies && !this.replies.isEmpty();
  }

  @Override
  public LocalDateTime getCreatedDate() {
    return null;
  }

  @Override
  public LocalDateTime getLastModifiedDate() {
    return null;
  }
}
