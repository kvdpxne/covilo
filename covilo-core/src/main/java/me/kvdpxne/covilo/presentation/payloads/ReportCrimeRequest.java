package me.kvdpxne.covilo.presentation.payloads;

import java.time.LocalDateTime;
import java.util.Collection;
import me.kvdpxne.covilo.presentation.dto.CategoryDto;
import me.kvdpxne.covilo.presentation.dto.CityDto;
import me.kvdpxne.covilo.presentation.dto.UserDto;

public record ReportCrimeRequest(
  String title,
  String description,
  Collection<CategoryDto> categories,
  LocalDateTime time,
  UserDto reporter,
  CityDto place,
  boolean confirmed
) { }