package me.kvdpxne.covilo.infrastructure.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.kvdpxne.covilo.Settings
import org.mariadb.jdbc.MariaDbDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration @Autowired(required = true) constructor(
  private val settings: Settings
) {

  @Primary
  @Bean(name = ["primaryDataSource"])
  fun getPrimaryDataSource(): DataSource {
    val source = DataSourceBuilder.create()
      .driverClassName("org.mariadb.jdbc.Driver")
      .type(MariaDbDataSource::class.java)
      .url("jdbc:mariadb://${settings.database}")
      .username(settings.user)
      .password(settings.password)
      .build()

    return HikariDataSource(
      HikariConfig().apply {
        dataSource = source
        poolName = "MariaDatabasePool"
        maximumPoolSize = 1
      }
    )
  }
}