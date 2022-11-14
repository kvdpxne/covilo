package me.kvdpxne.covilo.domain.models

import me.kvdpxne.covilo.utils.Recognizable
import java.io.Serializable

interface AccountRole : Recognizable, Serializable {

  val name: String
  val displayName: String
}