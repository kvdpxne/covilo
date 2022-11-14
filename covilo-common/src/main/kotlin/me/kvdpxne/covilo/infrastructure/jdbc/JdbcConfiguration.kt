package me.kvdpxne.covilo.infrastructure.jdbc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class JdbcConfiguration(datasource: DataSource) {

  @get:Bean
  val operations: NamedParameterJdbcOperations =
    NamedParameterJdbcTemplate(datasource)
}