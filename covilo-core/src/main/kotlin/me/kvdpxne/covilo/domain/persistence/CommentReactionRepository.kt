package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.CommentReaction
import me.kvdpxne.covilo.domain.model.CommentReactions
import java.util.UUID

/**
 * @see CommentReaction
 */
interface CommentReactionRepository {

  fun findByIdentifier(identifier: UUID): CommentReaction?
  fun findAll(): CommentReactions

  fun insert(reaction: CommentReaction)
  fun update(reaction: CommentReaction)

  fun delete(identifier: UUID)
  fun deleteAll()

  fun count(): Long
}