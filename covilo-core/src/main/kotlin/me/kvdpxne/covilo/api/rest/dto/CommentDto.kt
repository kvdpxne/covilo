package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.model.Comment
import me.kvdpxne.covilo.domain.model.Comments

/**
 * Shortcut to the [CommentDto] collection.
 */
typealias CommentsDto = Collection<CommentDto>

/**
 * TODO doc
 */
data class CommentDto(
  val content: String,
  val reactions: CommentReactionsDto
)

/**
 * Transforms the [Comment] entity to [CommentDto].
 */
fun Comment.toDto(): CommentDto {
  return CommentDto(
    this.content,
    this.reactions.toDto()
  )
}

/**
 * Transforms all [Comment] entities that are in the collection
 * and are not null to [CommentDto].
 */
fun Comments.toDto(): CommentsDto {
  return mapNotNull {
    it.toDto()
  }
}
