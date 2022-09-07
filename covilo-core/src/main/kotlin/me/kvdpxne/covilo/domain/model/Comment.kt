package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

/**
 * Shortcut to the [Comment] collection.
 */
typealias Comments = Collection<Comment>

data class Comment(
  val identifier: UUID = UUID.randomUUID(),
  val content: String,
  val reactions: CommentReactions = emptyList(),
  val author: User,
  override val createdDate: LocalDateTime = LocalDateTime.now(),
  override var lastModifiedDate: LocalDateTime? = null
) : Serializable, Auditable {

  companion object {
    private const val serialVersionUID: Long = 2107228125L
  }
}

data class CommentAuthor(
  val comment: Comment,
  val author: User
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1401404907L
  }
}