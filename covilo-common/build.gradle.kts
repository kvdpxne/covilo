plugins {
  alias(libraries.plugins.spring.boot)
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  val spring = "org.springframework.boot"
  annotationProcessor("$spring:spring-boot-configuration-processor")
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("$spring:spring-boot-starter-web")
  implementation("$spring:spring-boot-starter-data-rest")
  implementation("$spring:spring-boot-starter-jdbc")
  implementation("$spring:spring-boot-starter-data-jdbc")
  implementation(libraries.swagger)
//  implementation(libraries.mariadb.client)
  runtimeOnly("com.h2database:h2")
}