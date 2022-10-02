package me.kvdpxne.covilo.infrastructure.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfiguration {

  @Bean
  fun getCacheManager(): CacheManager {
    return ConcurrentMapCacheManager("classifications")
  }
}