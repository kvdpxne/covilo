package me.kvdpxne.covilo.infrastructure.jdbc.callback

import org.springframework.jdbc.core.RowCallbackHandler
import java.sql.ResultSet

class RowCounterCallback : RowCallbackHandler {

  var count: Int = 0

  override fun processRow(result: ResultSet) {
    count++
  }
}