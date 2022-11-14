package me.kvdpxne.covilo.domain.models

import java.time.LocalDateTime

interface Auditable {

  val createdDate: LocalDateTime
  var lastModifiedDate: LocalDateTime?

  fun wasModified(): Boolean = null != lastModifiedDate
}