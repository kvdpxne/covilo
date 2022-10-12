pluginManagement {

  repositories {
    gradlePluginPortal()

    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
  }

  plugins {
    id("org.springframework.boot").version("2.7.4")
    id("io.spring.dependency-management").version("1.0.14.RELEASE")
    kotlin("jvm").version("1.7.20")
    kotlin("plugin.spring").version("1.7.20")
  }
}

rootProject.name = "covilo"
include("covilo-core")
