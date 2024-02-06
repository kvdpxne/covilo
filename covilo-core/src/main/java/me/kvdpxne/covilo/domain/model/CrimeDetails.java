package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;

/**
 * Represents details related to a crime.
 *
 * <p>
 * This record contains information about a crime, including its unique
 * identifier, description, comments, and creation/modification timestamps.
 * </p>
 *
 * @param identifier       The unique identifier of the crime details.
 * @param description      The description of the crime.
 * @param comments         Comments related to the crime.
 * @param createdDate      The timestamp when the crime details were created.
 * @param lastModifiedDate The timestamp when the crime details were last modified.
 */
public record CrimeDetails(
  UUID identifier,
  String description,
  Set<Comment> comments,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) implements Identifiable<UUID>, Auditable {}
