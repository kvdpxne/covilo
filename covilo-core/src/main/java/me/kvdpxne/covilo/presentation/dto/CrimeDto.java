package me.kvdpxne.covilo.presentation.dto;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Crime;

public record CrimeDto(
  String identifier,
  String time,
  double latitude,
  double longitude,
  String[] categories,
  String reporterIdentifier,
  boolean confirmed,
  String createdDate,
  String lastModifiedDate
) {

  public static CrimeDto toCrimeDto(
    final Crime crime
  ) {
    return new CrimeDto(
      crime.getIdentifier(),
      crime.getTime().toString(),
      crime.getCoordinatesLatitude(),
      crime.getCoordinatesLatitude(),
      crime.getCategories()
        .stream()
        .map(Category::getIdentifier)
        .toArray(String[]::new),
      crime.getReporter().getIdentifier(),
      crime.isConfirmed(),
      crime.getCreatedDate().toString(),
      null != crime.getLastModifiedDate()
        ? crime.getLastModifiedDate().toString()
        : null
    );
  }
}
