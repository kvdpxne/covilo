package me.kvdpxne.covilo.utils

import java.time.format.DateTimeFormatter

val timestampFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss:")

val dateTimeFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

val dateFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("yyyy-MM-dd")