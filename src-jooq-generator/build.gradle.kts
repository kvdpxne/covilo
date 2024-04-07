plugins {
  id("java")
}

dependencies {
  libraries.versions.jooq.orNull?.let {
    implementation("org.jooq:jooq-codegen:$it")
  }
}