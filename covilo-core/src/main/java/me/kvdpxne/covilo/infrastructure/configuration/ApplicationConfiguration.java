package me.kvdpxne.covilo.infrastructure.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationConfiguration {

  @Value("${application.name}")
  private String name;

  @Value("${application.version}")
  private String version;
}
