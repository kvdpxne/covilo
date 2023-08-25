package me.kvdpxne.covilo.api;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.CityDto;
import me.kvdpxne.covilo.application.dto.CountryDto;
import me.kvdpxne.covilo.application.dto.ProvinceDto;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.model.Province;
import me.kvdpxne.covilo.domain.persistence.CityRepository;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import me.kvdpxne.covilo.domain.persistence.ProvinceRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/0.1.0/geographical", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GeographicalController {

  private final CountryRepository countryRepository;
  private final ProvinceRepository provinceRepository;
  private final CityRepository cityRepository;

  @GetMapping(path = "/countries")
  public ResponseEntity<Collection<CountryDto>> getCountries() {
    return ResponseEntity.ok(countryRepository.findAll().stream().map(country -> new CountryDto(
      country.getIdentifier(),
      country.getName(),
      country.getContinent().name()
    )).toList());
  }

  @GetMapping(path = "/provinces")
  public Collection<ProvinceDto> getProvinces(
    @RequestParam("country") UUID identifier
  ) {
    final Province sampler = Province.builder()
      .country(Country.builder().identifier(identifier).build())
      .build();

    final ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues();
    final Example<Province> example = Example.of(sampler, exampleMatcher);

    return provinceRepository.findAll(example).stream().map(it ->
      new ProvinceDto(
        it.getIdentifier(),
        it.getName(),
        it.getNationalName()
      )).toList();
  }

  @GetMapping(path = "/cities")
  public Collection<CityDto> getCities(
    @RequestParam(name = "province") UUID identifier
  ) {
    final City sampler = City.builder()
      .province(Province.builder().identifier(identifier).build())
      .build();

    System.out.println(sampler);

    final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
      .withIgnorePaths("population")
      .withIgnoreNullValues();

    final Example<City> example = Example.of(sampler, exampleMatcher);

    return cityRepository.findAll(example).stream().map(it -> new CityDto(
      it.getIdentifier(),
      it.getName(),
      it.getNationalName(),
      it.getCapital(),
      it.getPopulation()
    )).toList();
  }

  @GetMapping("/city")
  public CityDto getCity(@RequestParam UUID identifier) {
    return cityRepository.findById(identifier).map(it -> new CityDto(
      it.getIdentifier(),
      it.getName(),
      it.getNationalName(),
      it.getCapital(),
      it.getPopulation()
    )).orElse(null);
  }
}
