package me.kvdpxne.covilo.domain.model;

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
    return 0 == Double.compare(this.latitude, that.latitude) &&
      0 == Double.compare(this.longitude, that.longitude);
  }

  /**
   *
   */
  @Override
  public int hashCode() {
    int result = Double.hashCode(this.latitude);
    result = 31 * result + Double.hashCode(this.longitude);
    return result;
  }
}
