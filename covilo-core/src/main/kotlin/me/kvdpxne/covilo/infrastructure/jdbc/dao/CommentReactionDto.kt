package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_EMOJI
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_COMMENT_REACTION
import me.kvdpxne.covilo.domain.model.CommentReaction
import me.kvdpxne.covilo.domain.model.CommentReactions
import me.kvdpxne.covilo.domain.persistence.CommentReactionRepository
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CommentReactionMapper
import me.kvdpxne.covilo.util.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class CommentReactionDto @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : CommentReactionRepository {

  companion object {
    private const val T0 = TABLE_COMMENT_REACTION
    private const val C0 = COLUMN_IDENTIFIER
    private const val C1 = COLUMN_KEY
    private const val C2 = COLUMN_EMOJI
  }

  // frequently used query
  private val selectQuery = "SELECT $T0.$C0," +
    "$T0.$C1," +
    "$T0.$C2 " +
    "FROM $T0"

  override fun findByIdentifier(p0: UUID): CommentReaction? {
    val v0 = p0.toString()
    return runCatching {
      template.queryForObject(
        "$selectQuery WHERE $T0.$C0 = :$C0;",
        mapOf(C0 to v0),
        CommentReactionMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CommentReactions {
    return template.query(
      "$selectQuery;",
      CommentReactionMapper
    )
  }

  @Transactional
  override fun insert(p0: CommentReaction) {
    val v0 = p0.identifier.toString()
    val v1 = p0.key
    val v2 = p0.emoji
    template.update(
      "INSERT INTO $T0($C0,$C1,$C2) VALUES(:$C0,:$C1,:$C2);",
      mapOf(C0 to v0, C1 to v1, C2 to v2)
    )
  }

  @Transactional
  override fun update(p0: CommentReaction) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(p0: UUID) {
    val v0 = p0.toString()
    template.update(
      "DELETE FROM $T0 WHERE $C0 = :$C0",
      mapOf(C0 to v0)
    )
  }

  override fun deleteAll() {
    TODO("Not yet implemented")
  }

  override fun count(): Long {
    return template.count(C0, T0)
  }
}