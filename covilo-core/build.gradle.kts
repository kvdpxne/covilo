plugins {
  id("org.springframework.boot")
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {

  // Core starter, including auto-configuration support, logging and YAML.
  val starter = "org.springframework.boot:spring-boot-starter"
  implementation(starter)

  sequenceOf(
    "web",
    "jdbc",
    "data-jdbc",
    "security",
//    "oauth2-client",
//    "oauth2-resource-server"
  ).forEach {
    implementation("$starter-$it")
  }

  // Provides fast application restarts, LiveReload, and configurations for
  // enhanced development experience.
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  // Generate metadata for developers to offer contextual help and
  // "code completion" when working with custom configuration keys
  // (ex.application.properties/.yml files).
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  //
  implementation("org.mariadb.jdbc:mariadb-java-client:3.0.7")

  // MariaDB JDBC and R2DBC driver.
  runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}