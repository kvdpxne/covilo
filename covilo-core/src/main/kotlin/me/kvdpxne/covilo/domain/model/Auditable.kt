package me.kvdpxne.covilo.domain.model

import java.time.LocalDateTime

interface Auditable {

  val createdDate: LocalDateTime
  var lastModifiedDate: LocalDateTime?

  fun wasModified(): Boolean {
    return null != lastModifiedDate
  }
}