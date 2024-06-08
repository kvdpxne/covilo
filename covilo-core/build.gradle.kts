import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.gradle.api.tasks.testing.TestResult.ResultType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jooq.meta.jaxb.Logging

plugins {
  id("java")

  alias(libraries.plugins.spring.boot)
  alias(libraries.plugins.spring.management)

  id("nu.studer.jooq").version("9.0")
}

description = ""
version = "0.1.0"

configurations {
  compileOnly {
    extendsFrom(annotationProcessor.get())
  }
}

dependencies {
  // Spring
  implementation("org.springframework.boot:spring-boot-starter")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  // Spring Developer Tools
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  // Spring Web
  implementation("org.springframework.boot:spring-boot-starter-web")

  // Spring Security
  implementation("org.springframework.boot:spring-boot-starter-security")
  testImplementation("org.springframework.security:spring-security-test")

  // Spring SQL
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-jooq")
  runtimeOnly("org.postgresql:postgresql")

  // Spring I/O
  implementation("org.springframework.boot:spring-boot-starter-validation")

  // https://github.com/jwtk/jjwt
  libraries.jjwt.run {
    implementation(api)
    implementation(jackson)
    runtimeOnly(libraries.bundles.jjwt.runtime)
  }


  implementation(libraries.openapi)

  // Jooq
  jooqGenerator(project(":src-jooq-generator"))
  jooqGenerator("org.postgresql:postgresql")

  implementation("com.github.f4b6a3:ulid-creator:5.2.3")
//
//  // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.1")

  implementation(libraries.imageio.webp)
}

val targetJavaVersion = 22
java {
  val javaVersion = JavaVersion.toVersion(targetJavaVersion)

  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion

  if (JavaVersion.current() < javaVersion) {
    toolchain {
      languageVersion = JavaLanguageVersion.of(targetJavaVersion)
      vendor = JvmVendorSpec.ADOPTIUM
    }
  }
}

jooq {
  version = dependencyManagement.importedProperties["jooq.version"]
  edition = JooqEdition.OSS

  configurations {
    create("main") {

      jooqConfiguration.apply {
        logging = Logging.INFO
        jdbc.apply {
          driver = "org.postgresql.Driver"
          url = "jdbc:postgresql://localhost:5432/covilo"
          user = "postgres"
          password = "postgres"
        }
        generator.apply {
          val packageName2 = "me.kvdpxne.covilo.jooq"

          name = "org.jooq.codegen.DefaultGenerator"
          strategy.apply {
            name = "${packageName2}.DefaultGeneratorStrategyWithTableSuffix"
          }
          database.apply {
            name = "org.jooq.meta.postgres.PostgresDatabase"
            inputSchema = "public"
          }
//          target.apply {
//            packageName = "${packageName2}.main"
//          }
        }
      }
    }
  }
}

tasks {
  withType<JavaCompile>().configureEach {
    if (10 <= targetJavaVersion || JavaVersion.current().isJava10Compatible) {
      options.release.set(targetJavaVersion)
    }

    options.compilerArgs.add("--enable-preview")
  }

  withType<JavaExec>().configureEach {
    jvmArgs("--enable-preview")
  }

  named<JooqGenerate>("generateJooq").configure {
    // make jOOQ task participate in incremental builds (which is also a
    // prerequisite for build caching)
    allInputsDeclared = true
  }

  withType<ProcessResources>().configureEach {
    val properties = mapOf(
      "version" to project.version
    )

    inputs.properties(properties)
    filteringCharset = "UTF-8"

    filesMatching("application.yml") {
      expand(properties)
    }
  }

  val isDebugEnabled = logger.isDebugEnabled

  withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "testing")
    jvmArgs("--enable-preview", "-XX:+EnableDynamicAgentLoading")

    // fail the 'test' task on the first test failure
    failFast = true

    testLogging {
      events = setOf(TestLogEvent.FAILED)
      exceptionFormat = TestExceptionFormat.SHORT

      debug {
        exceptionFormat = TestExceptionFormat.FULL
      }
    }

    afterTest(KotlinClosure2({ descriptor: TestDescriptor, result: TestResult ->
      //
      val testClassName = descriptor.className
      val testName = descriptor.name
      val testDisplayName = descriptor.displayName

      var outputMessage = "Test [$testDisplayName]"
      if (isDebugEnabled) {
        outputMessage += " (${testClassName}#${testName}())"
      }

      when (result.resultType!!) {
        ResultType.SUCCESS -> {
          logger.lifecycle("$outputMessage > passed")
        }

        ResultType.FAILURE -> {
          logger.lifecycle("$outputMessage > FAILURE")
        }
        ResultType.SKIPPED -> {
          logger.lifecycle("$outputMessage > skipped")
        }
      }
    }))
  }
}