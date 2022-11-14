package me.kvdpxne.covilo.utils.sql

/**
 *
 */
class SqlRelation(
  val firstTable: String,
  val secondTable: String,
  val firstColumn: String,
  val secondColumn: String = "identifier"
)