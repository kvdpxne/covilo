package me.kvdpxne.covilo.domain.models

import me.kvdpxne.covilo.utils.Recognizable
import java.io.Serializable
import java.time.LocalDate

interface Account : Recognizable, Auditable, LoginCredentials, Serializable {

  var birthDate: LocalDate
  var enabled: Boolean
  val roles: Collection<String>
}