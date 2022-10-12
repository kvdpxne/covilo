package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_BIRTH_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_EMAIL
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_PASSWORD
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_USER
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseCreatedDate
import me.kvdpxne.covilo.util.sql.parseIdentifier
import me.kvdpxne.covilo.util.sql.parseLastModifiedDate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object UserMapper : RowMapper<User> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): User {
    val parser = ResultSetParser(TABLE_USER, result)

    // // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse remaining fields
    val email = parser.parse(COLUMN_EMAIL)!!
    val password = parser.parse(COLUMN_PASSWORD)!!
    val firstName = parser.parse(COLUMN_FIRST_NAME)
    val lastName = parser.parse(COLUMN_LAST_NAME)
    val birthDate = parser.parseDate(COLUMN_BIRTH_DATE)
    val createdDate = parser.parseCreatedDate()
    val lastModifiedDate = parser.parseLastModifiedDate()

    // Initialize
    return User(
      identifier,
      email,
      password,
      firstName,
      lastName,
      birthDate,
      createdDate,
      lastModifiedDate
    )
  }
}