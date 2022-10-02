package me.kvdpxne.covilo.util

import java.time.format.DateTimeFormatter

val dateTimeFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

val dateFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("yyyy-MM-dd")