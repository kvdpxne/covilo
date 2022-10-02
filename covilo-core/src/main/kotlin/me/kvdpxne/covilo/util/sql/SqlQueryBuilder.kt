package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.util.Buildable

interface SqlQueryBuilder : Buildable<String> {

  val parameters: MutableMap<String, String?>
}