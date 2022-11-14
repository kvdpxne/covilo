dependencies {

  implementation(project(":covilo-common"))

  //
  val spring = "org.springframework.boot"

  // Core starter, including auto-configuration support, logging and YAML
  implementation("$spring:spring-boot-starter")

  // Highly customizable authentication and access-control framework for
  // Spring applications
  implementation("$spring:spring-boot-starter-security")

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

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // Starter for testing Spring Boot applications with libraries
  // including JUnit Jupiter, Hamcrest and Mockito
  testImplementation("$spring:spring-boot-starter-test")

  testImplementation("org.springframework.security:spring-security-test")

  // https://mvnrepository.com/artifact/com.auth0/auth0
  implementation("com.auth0:java-jwt:4.1.0")
}