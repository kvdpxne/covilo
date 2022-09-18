package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.model.User
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_USER
import me.kvdpxne.covilo.util.sql.ResultSetParser
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object UserMapper : RowMapper<User> {

  override fun mapRow(result: ResultSet, row: Int): User {
    val parser = ResultSetParser(TABLE_USER, result)

    // // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Initialize
    TODO("Not yet implemented")
  }
}