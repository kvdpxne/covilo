package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER

/**
 *
 */
class SqlRelation(
  val firstTable: String,
  val secondTable: String,
  val firstColumn: String,
  val secondColumn: String = COLUMN_IDENTIFIER
)