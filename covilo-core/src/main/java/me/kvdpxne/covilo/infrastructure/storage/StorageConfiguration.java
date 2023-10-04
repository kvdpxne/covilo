package me.kvdpxne.covilo.infrastructure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("application.storage")
public class StorageConfiguration {

  /**
   *
   */
  private String parent;

  /**
   *
   */
  private String avatars;
}
