import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot").apply(false)
  id("io.spring.dependency-management").apply(false)
  kotlin("jvm")
  kotlin("plugin.spring").apply(false)
}

allprojects {
  description = "A simple crime reporting application."
  group = "me.kvdpxne"
  version = "0.0.1"
}

subprojects {

  apply(plugin = "kotlin")

  repositories {
    mavenCentral()
    mavenLocal()
  }

  dependencies {
    // Kotlin
    "implementation"(kotlin("stdlib-jdk8"))
    "implementation"(kotlin("reflect"))
  }

  tasks {

    withType<KotlinCompile> {
      kotlinOptions {
        suppressWarnings = true
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
      }
    }

    withType<Test> {
      useJUnitPlatform()
    }
  }
}

tasks {

  wrapper {
    // Complete Gradle distribution with binaries, sources and documentation
    distributionType = Wrapper.DistributionType.ALL
  }
}
