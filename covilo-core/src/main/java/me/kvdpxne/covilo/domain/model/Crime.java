package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;

public final class Crime
  implements Identifiable<UUID>, Auditable {

  private final UUID identifier;
  private final LocalDateTime time;
  private final Collection<UUID> categoryIdentifiers;
  private final UUID reporterIdentifier;
  private final boolean confirmed;
  private final LocalDateTime createdDate;
  private final LocalDateTime lastModifiedDate;

  public Crime(
    final UUID identifier,
    final LocalDateTime time,
    final Collection<UUID> categoryIdentifiers,
    final UUID reporterIdentifier,
    final boolean confirmed,
    final LocalDateTime createdDate,
    final LocalDateTime lastModifiedDate
  ) {
    this.identifier = identifier;
    this.time = time;
    this.categoryIdentifiers = categoryIdentifiers;
    this.reporterIdentifier = reporterIdentifier;
    this.confirmed = confirmed;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  public static CrimeBuilder builder() {
    return new CrimeBuilder();
  }

  public CrimeBuilder toBuilder() {
    return new CrimeBuilder(
      this.identifier,
      this.time,
      this.categoryIdentifiers,
      this.reporterIdentifier,
      this.confirmed,
      this.createdDate,
      this.lastModifiedDate
    );
  }

  @Override
  public UUID getIdentifier() {
    return this.identifier;
  }

  public LocalDateTime getTime() {
    return this.time;
  }

  public Collection<UUID> getCategoryIdentifiers() {
    return this.categoryIdentifiers;
  }

  public UUID getReporterIdentifier() {
    return this.reporterIdentifier;
  }

  public boolean isConfirmed() {
    return this.confirmed;
  }

  @Override
  public LocalDateTime getCreatedDate() {
    return this.createdDate;
  }

  @Override
  public LocalDateTime getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  public static final class CrimeBuilder
    implements Buildable<Crime> {

    private UUID identifier;
    private LocalDateTime time;
    private Collection<UUID> categoryIdentifiers;
    private UUID reporterIdentifier;
    private boolean confirmed;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CrimeBuilder(
      final UUID identifier,
      final LocalDateTime time,
      final Collection<UUID> categoryIdentifiers,
      final UUID reporterIdentifier,
      final boolean confirmed,
      final LocalDateTime createdDate,
      final LocalDateTime lastModifiedDate
    ) {
      this.identifier = identifier;
      this.time = time;
      this.categoryIdentifiers = categoryIdentifiers;
      this.reporterIdentifier = reporterIdentifier;
      this.confirmed = confirmed;
      this.createdDate = createdDate;
      this.lastModifiedDate = lastModifiedDate;
    }

    public CrimeBuilder() {
      // ..
    }

    public CrimeBuilder withIdentifier(final UUID identifier) {
      this.identifier = identifier;
      return this;
    }

    public CrimeBuilder withRandomIdentifier() {
      return withIdentifier(UUID.randomUUID());
    }

    public CrimeBuilder withTime(final LocalDateTime time) {
      this.time = time;
      return this;
    }

    public CrimeBuilder withCategoryIdentifiers(final Collection<UUID> categoryIdentifiers) {
      this.categoryIdentifiers = categoryIdentifiers;
      return this;
    }

    public CrimeBuilder withReporterIdentifier(final UUID reporterIdentifier) {
      this.reporterIdentifier = reporterIdentifier;
      return this;
    }

    public CrimeBuilder withConfirmed(final boolean confirmed) {
      this.confirmed = confirmed;
      return this;
    }

    public CrimeBuilder withCreatedDate(final LocalDateTime createdDate) {
      this.createdDate = createdDate;
      return this;
    }

    public CrimeBuilder withLastModifiedDate(final LocalDateTime lastModifiedDate) {
      this.lastModifiedDate = lastModifiedDate;
      return this;
    }

    @Override
    public Crime build() {
      return new Crime(
        this.identifier,
        this.time,
        this.categoryIdentifiers,
        this.reporterIdentifier,
        this.confirmed,
        this.createdDate,
        this.lastModifiedDate
      );
    }
  }
}
