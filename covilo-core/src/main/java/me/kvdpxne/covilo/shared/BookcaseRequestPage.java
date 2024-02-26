package me.kvdpxne.covilo.shared;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public final class BookcaseRequestPage {

  public static Pageable request(
    final BookAttributes attributes
  ) {
    // Requested number of elements per page.
    final int size = attributes.size();

    // Requested page number.
    final int page = attributes.page();

    //
    if (0 >= size || 0 >= page) {
      return Pageable.unpaged();
    }

    //
    return PageRequest.of(page, size);
  }
}
