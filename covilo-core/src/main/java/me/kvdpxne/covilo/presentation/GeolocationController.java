package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.presentation.dto.AdministrativeDivisionDto;
import me.kvdpxne.covilo.presentation.dto.BookDto;
import me.kvdpxne.covilo.presentation.dto.CityDto;
import me.kvdpxne.covilo.presentation.dto.CountryDto;
import me.kvdpxne.covilo.presentation.dto.RegionDto;
import me.kvdpxne.covilo.presentation.mappers.AdministrativeDivisionMapper;
import me.kvdpxne.covilo.presentation.mappers.CityMapper;
import me.kvdpxne.covilo.presentation.mappers.CountryMapper;
import me.kvdpxne.covilo.presentation.mappers.RegionMapper;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.model.Continent;
import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.domain.persistence.AdministrativeDivisionRepository;
import me.kvdpxne.covilo.domain.persistence.CityRepository;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import me.kvdpxne.covilo.domain.persistence.RegionRepository;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Endpoints.GEOLOCATION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GeolocationController {

  private final AdministrativeDivisionRepository administrativeDivisionRepository;

  private final CountryRepository countryRepository;
  private final CountryMapper countryMapper;
  private final RegionRepository provinceRepository;
  private final RegionMapper provinceMapper;
  private final CityRepository cityRepository;
  private final CityMapper cityMapper;
  private final AdministrativeDivisionMapper administrativeDivisionMapper;

  @PageableAsQueryParam
  @GetMapping(
    path = "divisions"
  )
  public BookDto<AdministrativeDivisionDto> getAdministrativeDivisions(
    @Parameter(hidden = true)
    final Pageable pageable
  ) {
    final int page = pageable.getPageNumber();
    final int size = pageable.getPageSize();

    return new BookDto<>(
      this.administrativeDivisionRepository.getAll(new BookAttributes(page, size))
        .getContent()
        .stream()
        .map(this.administrativeDivisionMapper::toDto)
        .toArray(AdministrativeDivisionDto[]::new),
      page,
      size
    );
  }

  @PageableAsQueryParam
  @GetMapping("countries")
  public ResponseEntity<BookDto<CountryDto>> getCountries(
    final AdministrativeDivision administrativeDivision,
    final Continent continent,
    @Parameter(hidden = true)
    final Pageable pageable
    ) {
    final int page = pageable.getPageNumber();
    final int size = pageable.getPageSize();

    return ResponseEntity.ok(new BookDto<>(
      this.countryRepository.getAll(new BookAttributes(page, size))
        .getContent()
        .stream()
        .map(this.countryMapper::toDto)
        .toArray(CountryDto[]::new),
      page,
      size
    ));
  }

  @PageableAsQueryParam
  @GetMapping("regions")
  public BookDto<RegionDto> getRegions(
    @RequestParam(name = "country", required = false)
    final UUID identifier,
    @Parameter(hidden = true)
    final Pageable pageable
  ) {
    final int page = pageable.getPageNumber();
    final int pageSize = pageable.getPageSize();

    Book<Region> box;
    if (null != identifier) {
      box = this.provinceRepository.findProvincesByCountryIdentifier(
        identifier,
        new BookAttributes(page, pageSize)
      );
    } else {
      box = this.provinceRepository.findProvinces(
        new BookAttributes(0, 0)
      );
    }

    final var content = box.getContent()
      .stream()
      .map(this.provinceMapper::toDto)
      .toArray(RegionDto[]::new);

    return new BookDto<>(content, page, content.length);

  }

  @PageableAsQueryParam
  @GetMapping("cities")
  public ResponseEntity<BookDto<CityDto>> getCities(
    @RequestParam(name = "country", required = false)
    final UUID countryIdentifier,
    @RequestParam(name = "provinceIdentifier", required = false)
    final UUID identifier,
    @Parameter(hidden = true)
    final Pageable pageable
  ) {
    final int page = pageable.getPageNumber();
    final int pageSize = pageable.getPageSize();

    final BookAttributes attributes = new BookAttributes(page, pageSize);
    final Book<City> book = null == identifier
      ? this.cityRepository.findCities(attributes)
      : this.cityRepository.findCitiesByProvinceIdentifier(identifier, attributes);

    return ResponseEntity.ok(new BookDto<>(
      book.getContent()
        .stream()
        .map(this.cityMapper::toDto)
        .toArray(CityDto[]::new),
      page,
      pageSize
    ));
  }

  @GetMapping("city")
  public ResponseEntity<CityDto> getCity(
    @RequestParam final UUID identifier
  ) {
    final City result = this.cityRepository.findByIdentifierOrNull(identifier);
    if (null == result) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(
      this.cityMapper.toDto(result)
    );
  }
}
