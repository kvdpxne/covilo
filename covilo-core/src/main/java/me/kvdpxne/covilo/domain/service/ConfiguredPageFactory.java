package me.kvdpxne.covilo.domain.service;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.exceptions.PageUnAllowedSizeException;
import me.kvdpxne.covilo.domain.model.pagination.EmptyPage;
import me.kvdpxne.covilo.domain.model.pagination.BasePage;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.model.pagination.UnlimitedPage;

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
   *
   */
  private <T> Page<T> createPage(
    final Supplier<? extends Collection<T>> contentProvider,
    final Function<Collection<T>, ? extends Page<T>> pageFunction
    ) {
    if (null == contentProvider) {
      return new EmptyPage<>();
    }

    final var content = contentProvider.get();
    if (null == content || content.isEmpty()) {
      return new EmptyPage<>();
    }

    return pageFunction.apply(content);
  }

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
    final Pageable pageable,
    final Supplier<? extends Collection<T>> contentProvider,
    final LongSupplier countProvider
  ) {
    if (null == pageable) {
      return this.createPage(
        contentProvider,
        it -> new BasePage<>(
          it,
          this.paginationConfiguration.getDefaultPageable(),
          countProvider.getAsLong()
        )
      );
    }

    final var size = pageable.getSize();
    if (Integer.MAX_VALUE == size
      && this.paginationConfiguration.isUnlimitedAllowed()) {
      return this.createPage(
        contentProvider,
        it -> new UnlimitedPage<>(
          it,
          pageable.getSortable()
        )
      );
    }

    final var max = this.paginationConfiguration.getMaximumPageSize();
    if (max < size) {
      throw new PageUnAllowedSizeException(
        STR."The maximum page size is \{max} and \{size} was used."
      );
    }

    return this.createPage(
      contentProvider,
      it -> new BasePage<>(
        it,
        pageable,
        countProvider.getAsLong()
      )
    );
  }
}
