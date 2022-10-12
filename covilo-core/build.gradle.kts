plugins {
  // Spring
  id("org.springframework.boot")
  id("io.spring.dependency-management")

  // Kotlin extension for Spring
  kotlin("plugin.spring")
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {

  //
  val spring = "org.springframework.boot"

  // Generate metadata for developers to offer contextual help and
  // "code completion" when working with custom configuration keys
  // (ex.application.properties/.yml files)
  annotationProcessor("$spring:spring-boot-configuration-processor")

  // Core starter, including auto-configuration support, logging and YAML
  implementation("$spring:spring-boot-starter")

  // Highly customizable authentication and access-control framework for
  // Spring applications
  implementation("$spring:spring-boot-starter-security")

  // Spring Boot integration for Spring Security's OAuth2/OpenID Connect
  // client features
  implementation("$spring:spring-boot-starter-oauth2-client")

  // Spring Boot integration for Spring Security's OAuth2 resource server
  // features
  implementation("$spring:spring-boot-starter-oauth2-resource-server")

  // Build web, including RESTful, applications using Spring MVC. Uses Apache
  // Tomcat as the default embedded container
  implementation("$spring:spring-boot-starter-web")

  // Exposing Spring Data repositories over REST via Spring Data REST
  implementation("$spring:spring-boot-starter-data-rest")

  // Database Connectivity API that defines how a client may connect and
  // query a database
  implementation("$spring:spring-boot-starter-jdbc")

  // Persist data in SQL stores with plain JDBC using Spring Data
  implementation("$spring:spring-boot-starter-data-jdbc")

  // Provides an API and implementations for managing user session information
  implementation("org.springframework.session:spring-session-jdbc")

  //
  implementation("org.mariadb.jdbc:mariadb-java-client:3.0.7")

  // MariaDB JDBC and R2DBC driver
  runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

  // Starter for testing Spring Boot applications with libraries
  // including JUnit Jupiter, Hamcrest and Mockito
  testImplementation("$spring:spring-boot-starter-test")

  testImplementation("org.springframework.security:spring-security-test")
}