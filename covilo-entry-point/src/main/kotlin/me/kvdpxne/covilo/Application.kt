package me.kvdpxne.covilo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(vararg arguments: String) {
  runApplication<Application>(*arguments)
}