package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

/**
 * Shortcut to the [CommentReaction] collection.
 */
typealias CommentReactions = Collection<CommentReaction>

data class CommentReaction(
  val identifier: UUID = UUID.randomUUID(),
  val key: String,
  val emoji: Char
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1142652165L
  }
}

data class CommentReactionCommentAuthor(
  val comment: Comment,
  val reaction: CommentReaction,
  val user: User,
  override val createdDate: LocalDateTime = LocalDateTime.now(),
  override var lastModifiedDate: LocalDateTime? = null
) : Serializable, Auditable {

  companion object {
    private const val serialVersionUID: Long = 1218130208L
  }
}