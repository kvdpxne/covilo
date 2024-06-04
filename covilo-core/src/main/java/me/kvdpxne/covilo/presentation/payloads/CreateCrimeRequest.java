package me.kvdpxne.covilo.presentation.payloads;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.User;

/**
 *
 */
public record CreateCrimeRequest(
  @Pattern(
    regexp = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})"
  )
  String datetime,
  String reporterIdentifier,

  @Valid
  CreateCategoryRequest[] createCategoryRequests,

  double latitude,
  double longitude
) {


  /**
   *
   */
  public Crime asCrime() {
    return Crime.builder()
      .withTime(this.datetime)
      .withReporter(
        User.builder()
          .withIdentifier(this.reporterIdentifier)
          .build()
      )
      .withCategories(() -> {
          if (null != this.createCategoryRequests) {
            return Arrays.stream(this.createCategoryRequests)
              .map(CreateCategoryRequest::asCategory)
              .toList();
          }
          return List.of();
        }
      )
      .withCoordinates(this.latitude, this.latitude)
      .build();
  }
}