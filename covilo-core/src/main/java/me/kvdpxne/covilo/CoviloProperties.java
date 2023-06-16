package me.kvdpxne.covilo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties("covilo")
public class CoviloProperties {

  private List<String> allowedOrigins;
}
