package me.kvdpxne.covilo.domain.service;

public class GeolocationService {

  public static final double EARTH_RADIUS = 6371; // Promień Ziemi w kilometrach

  public static final double KM = 0.009d;

  // Metoda do obliczania odległości między dwoma punktami na sferze (Ziemi)
  public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return EARTH_RADIUS * c;
  }

  // Metoda do obliczania współrzędnych geograficznych punktu na określonej odległości i kierunku od punktu początkowego
  public double[] calculateDestinationPoint(double lat1, double lon1, double distance, double bearing) {
    double angularDistance = distance / EARTH_RADIUS;
    double bearingRad = Math.toRadians(bearing);
    double lat1Rad = Math.toRadians(lat1);
    double lon1Rad = Math.toRadians(lon1);

    double lat2 = Math.asin(Math.sin(lat1Rad) * Math.cos(angularDistance) +
      Math.cos(lat1Rad) * Math.sin(angularDistance) * Math.cos(bearingRad));
    double lon2 = lon1Rad + Math.atan2(Math.sin(bearingRad) * Math.sin(angularDistance) * Math.cos(lat1Rad),
      Math.cos(angularDistance) - Math.sin(lat1Rad) * Math.sin(lat2));

    // Konwertowanie wyników z radianów na stopnie
    lat2 = Math.toDegrees(lat2);
    lon2 = Math.toDegrees(lon2);

    return new double[] {lat2, lon2};
  }

}
