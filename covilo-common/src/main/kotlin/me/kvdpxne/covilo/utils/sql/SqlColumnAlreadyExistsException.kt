package me.kvdpxne.covilo.utils.sql

/**
 * @param table table name
 * @param column column name in the table
 */
class SqlColumnAlreadyExistsException(
  table: String,
  column: String
) : RuntimeException("Column called $column already exists in table $table.")