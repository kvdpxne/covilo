package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;

/**
 * Represents a crime record.
 *
 * <p>
 * This record contains information about a specific crime, including its
 * identifier, time, location, classification, categories, reporter, crime
 * details, confirmation status, and creation/modification timestamps.
 * </p>
 *
 * @param identifier       The unique identifier of the crime.
 * @param time             The time at which the crime occurred.
 * @param place            The city where the crime occurred.
 * @param classification   The classification of the crime.
 * @param categories       The categories associated with the crime.
 * @param reporter         The user who reported the crime.
 * @param crimeDetails     Details about the crime.
 * @param confirmed        Indicates whether the crime has been confirmed.
 * @param createdDate      The timestamp when the crime record was created.
 * @param lastModifiedDate The timestamp when the crime record was last modified.
 */
@Builder(toBuilder = true)
public record Crime(
  UUID identifier,
  LocalDateTime time,
  City place,
  Classification classification,
  Set<Category> categories,
  User reporter,
  CrimeDetails crimeDetails,
  boolean confirmed,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) implements Identifiable<UUID>, Auditable {}
