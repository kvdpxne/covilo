package me.kvdpxne.covilo.infrastructure.jdbc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class JdbcConfiguration @Autowired constructor(
  private val dataSource: DataSource
) {

  @Bean
  fun getOperations(): NamedParameterJdbcTemplate {
    return NamedParameterJdbcTemplate(dataSource)
  }
}

