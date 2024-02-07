package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;

@Slf4j
@RequiredArgsConstructor
public final class SystematizationService
  implements SystematizationService2 {

  private final ClassificationRepository classificationRepository;

  private final CategoryRepository categoryRepository;

  @Override
  public Classification getClassificationByIdentifier(
    final UUID identifier
  ) {
    return this.classificationRepository.findClassificationByIdentifier(identifier)
      .orElseThrow();
  }

  @Override
  public Category getCategoryByIdentifier(final UUID identifier) {
    return this.categoryRepository.getByIdentifier(identifier)
      .orElseThrow();
  }
}
