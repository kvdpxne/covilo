package me.kvdpxne.covilo.infrastructure.data.sources

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.kvdpxne.covilo.ApplicationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.Locale
import javax.sql.DataSource

@Configuration(value = "data-sources-configuration")
class DataSourcesConfiguration(
  private val applicationProperties: ApplicationProperties
) {

  //  @Primary
//  @Bean(name = ["primaryDataSource"])
//  fun getPrimaryDataSource(): DataSource {
//    val source = DataSourceBuilder.create()
//      .driverClassName("org.mariadb.jdbc.Driver")
//      .type(MariaDbDataSource::class.java)
//      .url("jdbc:mariadb://${settings.database}")
//      .username(settings.user)
//      .password(settings.password)
//      .build()
//
//    return HikariDataSource(
//      HikariConfig().apply {
//        dataSource = source
//        poolName = "MariaDatabasePool"
//        maximumPoolSize = 1
//      }
//    )
//  }

  @Primary
  @Bean(name = ["primary-data-source"])
  fun getPrimaryDataSource(): DataSource {
    val name = applicationProperties.name.lowercase(Locale.ENGLISH)
    val dataSource = DataSourceBuilder.create()
      .driverClassName("org.h2.Driver")
      .url("jdbc:h2:mem:$name")
      .username("sa")
      .build()
    return HikariDataSource(
      HikariConfig().apply {
        this.dataSource = dataSource
        this.maximumPoolSize = 1
      }
    )
  }
}