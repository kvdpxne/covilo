package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;
import me.kvdpxne.covilo.shared.ApacheEqualsBuilder;
import me.kvdpxne.covilo.shared.ApacheHashCodeBuilder;

public final class Comment
  implements Identifiable<String>, Auditable {

  private final String identifier;
  private final String content;
  private final User author;
  private final Collection<Comment> replies;
  private final LocalDateTime createdDate;
  private final LocalDateTime lastModifiedDate;

  public Comment(
    final String identifier,
    final String content,
    final User author,
    final Collection<Comment> replies,
    final LocalDateTime createdDate,
    final LocalDateTime lastModifiedDate
  ) {
    this.identifier = identifier;
    this.content = content;
    this.author = author;
    this.replies = replies;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  public String getContent() {
    return this.content;
  }

  public User getAuthor() {
    return this.author;
  }

  public Collection<Comment> getReplies() {
    return this.replies;
  }

  @Override
  public LocalDateTime getCreatedDate() {
    return this.createdDate;
  }

  @Override
  public LocalDateTime getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (null == o || this.getClass() != o.getClass()) {
      return false;
    }

    final var that = (Comment) o;
    return new ApacheEqualsBuilder()
      .appendIgnoreCase(this.identifier, that.identifier)
      .append(this.content, that.content)
      .append(this.author, that.author)
      .append(this.replies, that.replies)
      .append(this.createdDate, that.createdDate)
      .append(this.lastModifiedDate, that.lastModifiedDate)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new ApacheHashCodeBuilder()
      .appendIgnoreCase(this.identifier)
      .append(this.content)
      .append(this.author)
      .append(this.replies)
      .append(this.createdDate)
      .append(this.lastModifiedDate)
      .toHashCode();
  }
}
