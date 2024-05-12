package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.domain.model.pagination.BasePageable;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.service.PaginationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaginationConfigurationImpl
  implements PaginationConfiguration {

  /**
   * The default page size.
   */
  @Value("${application.pagination.size.default}")
  private int defaultPageSize = 20;

  /**
   * The maximum allowed page size.
   */
  @Value("${application.pagination.size.maximum}")
  private int maximumPageSize = 100;

  @Override
  public int getDefaultPageSize() {
    return this.defaultPageSize;
  }

  @Override
  public int getMaximumPageSize() {
    return this.maximumPageSize;
  }

  @Override
  public Pageable getDefaultPageable() {
    return new BasePageable(0, this.getMaximumPageSize());
  }
}
