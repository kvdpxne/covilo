package me.kvdpxne.covilo.util.slf4j

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun logger(clazz: KClass<*>): Logger {
  return LoggerFactory.getLogger(clazz::class.java)
}