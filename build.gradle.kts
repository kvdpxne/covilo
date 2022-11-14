import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
  val libraries = libraries.plugins

  alias(libraries.kotlin.jvm).apply(false)
  alias(libraries.kotlin.spring).apply(false)
  alias(libraries.spring.boot).apply(false)
  alias(libraries.spring.management)
}

allprojects {
  description = "A simple crime reporting application."
  group = "me.kvdpxne"
  version = "0.0.1"
}

subprojects {

  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "org.jetbrains.kotlin.plugin.spring")
  apply(plugin = "org.springframework.boot")
  apply(plugin = "io.spring.dependency-management")

  if (name != "covilo-entry-point") {
    dependencyManagement {
      imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
      }
    }
  }

  dependencies {
    // Kotlin
    "implementation"(kotlin("stdlib-jdk8"))
    "implementation"(kotlin("reflect"))

    // Starter for testing Spring Boot applications with libraries
    // including JUnit Jupiter, Hamcrest and Mockito
    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
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
