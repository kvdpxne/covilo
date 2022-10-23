package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.models.AuthenticationToken
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_EXPIRATION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_TOKEN
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_AUTHENTICATION_TOKEN
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseIdentifier
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object AuthenticationTokenMapper : RowMapper<AuthenticationToken> {

  override fun mapRow(result: ResultSet, row: Int): AuthenticationToken {
    val parser = ResultSetParser(TABLE_AUTHENTICATION_TOKEN, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse relational entities to the entity
    val user = UserMapper.mapRow(result, row)

    // Parse remaining fields
    val token = parser.parse(COLUMN_TOKEN)!!
    val expiration = parser.parseDateTime(COLUMN_EXPIRATION)!!

    // Initialize
    return AuthenticationToken(
      identifier,
      token,
      expiration,
      user
    )
  }
}