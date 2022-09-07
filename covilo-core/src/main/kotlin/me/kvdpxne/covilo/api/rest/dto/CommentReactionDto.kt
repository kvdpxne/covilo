package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.model.CommentReaction
import me.kvdpxne.covilo.domain.model.CommentReactions

/**
 * Shortcut to the [CommentReactionDto] collection.
 */
typealias CommentReactionsDto = Collection<CommentReactionDto>

/**
 * TODO doc
 */
data class CommentReactionDto(
  val key: String,
  val emoji: Char
)

/**
 * Transforms the [CommentReaction] entity to [CommentReactionDto].
 */
fun CommentReaction.toDto(): CommentReactionDto {
  return CommentReactionDto(
    this.key,
    this.emoji
  )
}

/**
 * Transforms all [CommentReaction] entities that are in the collection
 * and are not null to [CommentReactionDto].
 */
fun CommentReactions.toDto(): CommentReactionsDto {
  return mapNotNull {
    it.toDto()
  }
}