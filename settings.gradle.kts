pluginManagement {

  repositories {
    gradlePluginPortal()

    mavenCentral()
    mavenLocal()

    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {

  versionCatalogs {
    val fileName = "libraries"
    create(fileName) {
      from(files("gradle/$fileName.versions.toml"))
    }
  }

  repositories {
    mavenCentral()
    mavenLocal()

    maven {
      url = uri("https://jitpack.io")
      content {
        includeGroupAndSubgroups("com.github.kvdpxne")
        includeGroup("com.github.gotson")
      }
    }

    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
  }

  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}

sequenceOf(
  "covilo-core",
  "src-jooq-generator"
).forEach {
  include(it)
}

rootProject.name = "covilo"