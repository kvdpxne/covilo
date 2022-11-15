package me.kvdpxne.covilo.infrastructure.jdbc

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations

fun NamedParameterJdbcOperations.operations(): JdbcOperations {
  return this.jdbcOperations
}