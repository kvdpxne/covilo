package me.kvdpxne.covilo.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.domain.persistence.AdministrativeDivisionRepository;
import me.kvdpxne.covilo.domain.persistence.CityRepository;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import me.kvdpxne.covilo.domain.persistence.RegionRepository;
import me.kvdpxne.covilo.domain.port.out.GeolocationServicePort;

@Slf4j
@RequiredArgsConstructor
public final class GeolocationService
  implements GeolocationServicePort {

  private final AdministrativeDivisionRepository administrativeDivisionRepository;
  private final CityRepository cityRepository;
  private final CountryRepository countryRepository;
  private final RegionRepository regionRepository;

  @Override
  public List<AdministrativeDivision> getAdministrativeDivisions() {
    return this.administrativeDivisionRepository.getAll();
  }

  @Override
  public List<Country> getCountries() {
    return this.countryRepository.getAll();
  }

  @Override
  public List<Region> getRegions() {
    return null;
  }

  @Override
  public List<City> getCities() {
    return null;
  }
}
