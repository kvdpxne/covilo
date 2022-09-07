package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_EMOJI
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_COMMENT_REACTION
import me.kvdpxne.covilo.domain.model.CommentReaction
import me.kvdpxne.covilo.util.sql.getChar
import me.kvdpxne.covilo.util.sql.getUuid
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CommentReactionMapper : RowMapper<CommentReaction> {

  private const val T0 = TABLE_COMMENT_REACTION
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_KEY
  private const val C2 = COLUMN_EMOJI

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): CommentReaction {
    val v0 = result.getUuid("$T0.$C0")
    val v1 = result.getString("$T0.$C1")
    val v2 = result.getChar("$T0.$C2")
    return CommentReaction(
      identifier = v0,
      key = v1,
      emoji = v2
    )
  }
}