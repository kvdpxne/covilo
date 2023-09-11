package me.kvdpxne.covilo.api;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.CityDto;
import me.kvdpxne.covilo.application.dto.CountryDto;
import me.kvdpxne.covilo.application.dto.ProvinceDto;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CityEntity;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CityDao;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CountryDao;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ProvinceDao;
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

  private final CountryDao countryRepository;
  private final ProvinceDao provinceRepository;
  private final CityDao cityRepository;

  @GetMapping(path = "/countries")
  public ResponseEntity<Collection<CountryDto>> getCountries() {
    return ResponseEntity.ok(countryRepository.findAll().stream().map(country -> new CountryDto(
      country.getIdentifier(),
      country.getName(),
      country.getContinent().name(),
      country.getAdministrativeDivisionType().name()
    )).toList());
  }

  @GetMapping(path = "/provinces")
  public Collection<ProvinceDto> getProvinces(
    @RequestParam("country") UUID identifier
  ) {
    final ProvinceEntity sampler = ProvinceEntity.builder()
      .country(CountryEntity.builder().identifier(identifier).build())
      .build();

    final ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues();
    final Example<ProvinceEntity> example = Example.of(sampler, exampleMatcher);

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
    final CityEntity sampler = CityEntity.builder()
      .province(ProvinceEntity.builder().identifier(identifier).build())
      .build();

    final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
      .withIgnorePaths("population")
      .withIgnoreNullValues();

    final Example<CityEntity> example = Example.of(sampler, exampleMatcher);

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
