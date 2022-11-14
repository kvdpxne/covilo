package me.kvdpxne.covilo

import me.kvdpxne.covilo.domain.models.CapitalType
import me.kvdpxne.covilo.utils.sql.ResultSetParser

fun ResultSetParser.age(): UInt =
  parseInt(AGE_COLUMN).toUInt()

fun ResultSetParser.key(): String =
  parse(KEY_COLUMN)!!

fun ResultSetParser.capital(): CapitalType =
  parseEnum(CAPITAL_COLUMN, CapitalType.values()) ?: CapitalType.NONE

fun ResultSetParser.caught(): Boolean =
  parseBoolean(CAUGHT_COLUMN)

fun ResultSetParser.confirmed(): Boolean =
  parseBoolean(CONFIRMED_COLUMN)

fun ResultSetParser.description(): String =
  parse(DESCRIPTION_COLUMN)!!

fun ResultSetParser.domesticName(): String =
  parse(DOMESTIC_NAME_COLUMN)!!

fun ResultSetParser.firstName(): String? =
  parse(FIRST_NAME_COLUMN)

fun ResultSetParser.lastName(): String? =
  parse(LAST_NAME_COLUMN)

fun ResultSetParser.population(): UInt =
  parseInt(POPULATION_COLUMN).toUInt()