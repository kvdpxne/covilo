package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Classification;

public interface SystematizationService2 {

  Classification getClassificationByIdentifier(
    final UUID identifier
  );

  Category getCategoryByIdentifier(
    final UUID identifier
  );


}
