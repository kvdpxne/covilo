package me.kvdpxne.covilo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "application")
class ApplicationProperties {

  lateinit var name: String
  lateinit var version: String
}