package me.kvdpxne.covilo.shared;

import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Mapper interface for converting PageRange objects to Spring PageRequest
 * objects.
 */
@Component
public final class SpringPageRequestMapper {

  /**
   * Converts a PageRange object to a Spring PageRequest object.
   *
   * @param range The PageRange object to convert.
   * @return The corresponding Spring PageRequest object.
   */
  public PageRequest toPageRequest(final PageRange range) {
    return PageRequest.of(range.page(), range.size());
  }

  public PageRange toPageRange(final Pageable page) {
    return null != page
      ? PageRange.of(page.getPageNumber(), page.getPageSize())
      : PageRange.DEFAULT_PAGE_RANGE;
  }
}
