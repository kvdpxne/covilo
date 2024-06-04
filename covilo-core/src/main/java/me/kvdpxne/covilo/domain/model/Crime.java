package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.function.Supplier;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;
import me.kvdpxne.covilo.infrastructure.uid.Uid;

@SuppressWarnings("LombokGetterMayBeUsed")
public final class Crime
  implements Identifiable<String>, Auditable {

  private final String               identifier;
  private final OffsetDateTime       time;
  private final Coordinates          coordinates;
  private final Collection<Category> categories;
  private final User reporter;
  private final boolean              confirmed;
  private final LocalDateTime        createdDate;
  private final LocalDateTime        lastModifiedDate;

  public Crime(
    final String identifier,
    final OffsetDateTime time,
    final Coordinates coordinates,
    final Collection<Category> categories,
    final User reporter,
    final boolean confirmed,
    final LocalDateTime createdDate,
    final LocalDateTime lastModifiedDate
  ) {
    this.identifier = identifier;
    this.time = time;
    this.coordinates = coordinates;
    this.categories = categories;
    this.reporter = reporter;
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
      this.coordinates,
      this.categories,
      this.reporter,
      this.confirmed,
      this.createdDate,
      this.lastModifiedDate
    );
  }

  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  public OffsetDateTime getTime() {
    return this.time;
  }

  public Coordinates getCoordinates() {
    return this.coordinates;
  }

  public double getCoordinatesLatitude() {
    return this.getCoordinates().getLatitude();
  }

  public double getCoordinatesLongitude() {
    return this.getCoordinates().getLongitude();
  }

  public Collection<Category> getCategories() {
    return this.categories;
  }

  public User getReporter() {
    return this.reporter;
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

    private String identifier;
    private OffsetDateTime time;
    private Coordinates coordinates;
    private Collection<Category> categories;
    private User reporter;
    private boolean confirmed;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CrimeBuilder(
      final String identifier,
      final OffsetDateTime time,
      final Coordinates coordinates,
      final Collection<Category> categories,
      final User reporter,
      final boolean confirmed,
      final LocalDateTime createdDate,
      final LocalDateTime lastModifiedDate
    ) {
      this.identifier = identifier;
      this.time = time;
      this.coordinates = coordinates;
      this.categories = categories;
      this.reporter = reporter;
      this.confirmed = confirmed;
      this.createdDate = createdDate;
      this.lastModifiedDate = lastModifiedDate;
    }

    public CrimeBuilder() {
      // ..
    }

    public CrimeBuilder withIdentifier(final String identifier) {
      this.identifier = identifier;
      return this;
    }

    public CrimeBuilder withRandomIdentifier() {
      this.identifier = Uid.next();
      return this;
    }

    public CrimeBuilder withTime(final OffsetDateTime offsetDateTime) {
      this.time = offsetDateTime;
      return this;
    }

    public CrimeBuilder withTime(final String time) {
      this.time = OffsetDateTime.parse(time);
      return this;
    }

    public CrimeBuilder withCoordinates(final Coordinates coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    public CrimeBuilder withCoordinates(final double latitude,
                                        final double longitude) {
      return this.withCoordinates(new Coordinates(longitude, latitude));
    }

    public CrimeBuilder withCategories(final Collection<Category> categoryIdentifiers) {
      this.categories = categoryIdentifiers;
      return this;
    }

    public CrimeBuilder withCategories(final Supplier<Collection<Category>> categories) {
      return this.withCategories(categories.get());
    }

    public CrimeBuilder withReporter(final User reporter) {
      this.reporter = reporter;
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

    public CrimeBuilder withCurrentDateAsCreatedDate() {
      return this.withCreatedDate(LocalDateTime.now());
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
        this.coordinates,
        this.categories,
        this.reporter,
        this.confirmed,
        this.createdDate,
        this.lastModifiedDate
      );
    }
  }
}
