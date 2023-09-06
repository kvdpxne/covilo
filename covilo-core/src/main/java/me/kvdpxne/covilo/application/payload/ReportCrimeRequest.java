package me.kvdpxne.covilo.application.payload;

import java.time.LocalDateTime;
import java.util.Collection;
import me.kvdpxne.covilo.application.dto.CategoryDto;
import me.kvdpxne.covilo.application.dto.CityDto;
import me.kvdpxne.covilo.application.dto.UserDto;

public record ReportCrimeRequest(
  String title,
  String description,
  Collection<CategoryDto> categories,
  LocalDateTime time,
  UserDto reporter,
  CityDto place,
  boolean confirmed
) { }