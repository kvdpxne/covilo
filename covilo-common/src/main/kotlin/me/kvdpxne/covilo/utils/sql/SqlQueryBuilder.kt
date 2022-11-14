package me.kvdpxne.covilo.utils.sql

import me.kvdpxne.covilo.utils.Buildable

interface SqlQueryBuilder : Buildable<String> {

  val parameters: MutableMap<String, String?>
}