package me.kvdpxne.covilo.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

fun <T : Any> logger(ofClass: Class<T>): Logger {
  val enclosing = ofClass.enclosingClass
  val unwrapped = enclosing?.takeIf {
    ofClass == enclosing.kotlin.companionObject?.java
  } ?: ofClass
  return LoggerFactory.getLogger(unwrapped.name)
}

fun <T : Any> T.logger(): Lazy<Logger> {
  return lazy {
    logger(this.javaClass)
  }
}