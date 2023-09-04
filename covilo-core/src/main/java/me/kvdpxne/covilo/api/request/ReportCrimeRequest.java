package me.kvdpxne.covilo.api.request;

import java.time.LocalDateTime;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.model.User;

public record ReportCrimeRequest(
  LocalDateTime time,
  City place,
  User reporter,
  Category category,
  String description,
  boolean confirmed
) { }
