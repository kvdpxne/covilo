package me.kvdpxne.covilo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "covilo")
class Settings {

  var database: String = "localhost:3306/covilo"
  var user: String = "root"
  var password: String = ""

  var origins = listOf("http://localhost:4200")
}