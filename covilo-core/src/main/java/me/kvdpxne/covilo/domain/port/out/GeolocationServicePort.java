package me.kvdpxne.covilo.domain.port.out;

import java.util.List;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.model.Region;
import org.jetbrains.annotations.TestOnly;

public interface GeolocationServicePort {

  /**
   * @throws StackOverflowError
   */
  @TestOnly
  List<AdministrativeDivision> getAdministrativeDivisions();

  /**
   * @throws StackOverflowError
   */
  @TestOnly
  List<Country> getCountries();

  /**
   * @throws StackOverflowError
   */
  @TestOnly
  List<Region> getRegions();

  /**
   * @throws StackOverflowError
   */
  @TestOnly
  List<City> getCities();
}
