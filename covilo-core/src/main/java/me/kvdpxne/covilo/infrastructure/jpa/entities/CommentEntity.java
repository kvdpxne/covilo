package me.kvdpxne.covilo.infrastructure.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a comment entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {

  /**
   * The unique identifier for the comment.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "identifier", nullable = false)
  private UUID identifier;

  /**
   * The content of the comment.
   */
  @Column(name = "content")
  private String content;

  /**
   * The author of the comment.
   */
  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "author_identifier")
  private UserEntity author;

  /**
   * The replies to the comment.
   */
  @ManyToMany
  @JoinTable(
    name = "comment_replies",
    joinColumns = @JoinColumn(
      name = "comment_identifier"
    ),
    inverseJoinColumns = @JoinColumn(
      name = "reply_identifier"
    )
  )
  private Collection<CommentEntity> replies = new LinkedHashSet<>();
}
