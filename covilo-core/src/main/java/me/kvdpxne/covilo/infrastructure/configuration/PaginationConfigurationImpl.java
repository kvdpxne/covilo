package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.domain.model.pagination.BasePageable;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.service.PaginationConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaginationConfigurationImpl
  implements PaginationConfiguration {

  @Override
  public Pageable getDefaultPageable() {
    return new BasePageable(0, 20);
  }
}
