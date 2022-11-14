description = ""

plugins {
  alias(libraries.plugins.spring.boot)
}

dependencies {
  implementation(project(":${rootProject.name}-api"))
  implementation("org.springframework.boot:spring-boot-starter")
}