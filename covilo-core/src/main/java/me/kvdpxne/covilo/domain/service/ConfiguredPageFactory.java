package me.kvdpxne.covilo.domain.service;

import java.util.Collection;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.exceptions.PageUnAllowedSizeException;
import me.kvdpxne.covilo.domain.model.pagination.BasePage;
import me.kvdpxne.covilo.domain.model.pagination.EmptyPage;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Factory class for creating pages with configurable pagination settings.
 */
@RequiredArgsConstructor
public final class ConfiguredPageFactory {

  /**
   * The pagination configuration used by this factory.
   */
  private final PaginationConfiguration paginationConfiguration;

  /**
   * Creates a page with the given content, pageable configuration, and total
   * number of elements.
   *
   * @param pageable        The pageable configuration.
   * @param contentProvider A supplier for providing the content of the page.
   * @param countProvider   A supplier for providing the total count of
   *                        elements.
   * @param <T>             The type of elements in the page.
   * @return A page containing the provided content.
   * @throws PageUnAllowedSizeException If the specified page size exceeds the
   *                                    maximum allowed size.
   */
  public <T> Page<T> createPage(
    Pageable pageable,
    final Supplier<? extends Collection<T>> contentProvider,
    final LongSupplier countProvider
  ) {
    if (null != pageable) {

      final var size = pageable.getSize();
      final var maximumSize = this.paginationConfiguration.getMaximumPageSize();

      if (maximumSize < size) {
        throw new PageUnAllowedSizeException(
          STR."The maximum page size is \{maximumSize} and \{size} was used."
        );
      }
    } else {
      pageable = this.paginationConfiguration.getDefaultPageable();
    }

    if (null == contentProvider) {
      return new EmptyPage<>();
    }

    final var content = contentProvider.get();
    if (null == content || content.isEmpty()) {
      return new EmptyPage<>();
    }

    return new BasePage<>(
      content,
      pageable,
      countProvider.getAsLong()
    );
  }
}
