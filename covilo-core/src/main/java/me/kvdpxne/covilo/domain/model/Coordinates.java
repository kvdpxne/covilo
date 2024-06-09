package me.kvdpxne.covilo.domain.model;

import me.kvdpxne.covilo.shared.ApacheEqualsBuilder;
import me.kvdpxne.covilo.shared.ApacheHashCodeBuilder;

/**
 * Represents geographic coordinates consisting of latitude and longitude.
 */
public final class Coordinates {

  private final double latitude;
  private final double longitude;

  /**
   * Constructs a new {@link Coordinates} object with the given latitude and
   * longitude.
   *
   * @param latitude  The latitude value.
   * @param longitude The longitude value.
   */
  public Coordinates(
    final double latitude,
    final double longitude
  ) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Retrieves the latitude value.
   *
   * @return The latitude value.
   */
  public double getLatitude() {
    return this.latitude;
  }

  /**
   * Retrieves the longitude value.
   *
   * @return The longitude value.
   */
  public double getLongitude() {
    return this.longitude;
  }

  /**
   * Calculates the distance between a point represented by this object and
   * another point on Earth using the Haversine formula.
   *
   * @param latitude  The latitude of the other point in degrees.
   * @param longitude The longitude of the other point in degrees.
   * @return The distance between the two points in kilometers.
   */
  public double distance(
    final double latitude,
    final double longitude
  ) {
    // Convert degrees to radians
    double dLat = Math.toRadians(latitude - this.latitude);
    double dLon = Math.toRadians(longitude - this.longitude);

    // Calculate the Haversine formula intermediate terms
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
      + Math.cos(Math.toRadians(this.latitude))
      * Math.cos(Math.toRadians(latitude))
      * Math.sin(dLon / 2) * Math.sin(dLon / 2);

    // Calculate the central angle using the Haversine formula
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    // Return the distance multiplied by Earth's radius
    return 6371 * c;
  }

  /**
   * Calculates the distance between this object and another point represented
   * by a {@link Coordinates} object.
   *
   * @param coordinates The {@link Coordinates} object representing the other
   *                    point.
   * @return The distance between the two points in kilometers.
   */
  public double distance(
    final Coordinates coordinates
  ) {
    // Delegate the calculation to the other distance method
    return this.distance(
      coordinates.getLatitude(),
      coordinates.getLongitude()
    );
  }

  /**
   *
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (null == o || this.getClass() != o.getClass()) {
      return false;
    }

    final var that = (Coordinates) o;
    return new ApacheEqualsBuilder()
      .append(this.latitude, that.latitude)
      .append(this.longitude, that.longitude)
      .isEquals();
  }

  /**
   *
   */
  @Override
  public int hashCode() {
    return new ApacheHashCodeBuilder()
      .append(this.latitude)
      .append(this.longitude)
      .toHashCode();
  }
}
