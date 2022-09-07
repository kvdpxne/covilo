package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.Comment
import me.kvdpxne.covilo.domain.model.Comments
import java.util.UUID

/**
 * @see Comment
 */
interface CommentRepository {

  fun findByIdentifier(identifier: UUID): Comment?
  fun findAll(): Comments

  fun insert(comment: Comment)
  fun update(comment: Comment)

  fun delete(identifier: UUID)
  fun deleteAll()

  fun count(): Long
}