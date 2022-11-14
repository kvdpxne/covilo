rootProject.name = "covilo"

pluginManagement {

  repositories {
    gradlePluginPortal()

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

  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    mavenCentral()
    mavenLocal()
  }
}

sequenceOf(
  "entry-point",
  "api",
  "authentication",
  "common"
).forEach {
  include("${rootProject.name}-$it")
}
