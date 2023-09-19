package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.BookDto;
import me.kvdpxne.covilo.application.dto.CityDto;
import me.kvdpxne.covilo.application.dto.CountryDto;
import me.kvdpxne.covilo.application.dto.ProvinceDto;
import me.kvdpxne.covilo.application.mapper.ICityMapper;
import me.kvdpxne.covilo.application.mapper.ICountryMapper;
import me.kvdpxne.covilo.application.mapper.IProvinceMapper;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.persistence.ICityRepository;
import me.kvdpxne.covilo.domain.persistence.ICountryRepository;
import me.kvdpxne.covilo.domain.persistence.IProvinceRepository;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/0.1.0/geographical", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GeographicalController {

  private final ICountryRepository  countryRepository;
  private final ICountryMapper      countryMapper;
  private final IProvinceRepository provinceRepository;
  private final IProvinceMapper     provinceMapper;
  private final ICityRepository     cityRepository;
  private final ICityMapper         cityMapper;

  @PageableAsQueryParam
  @GetMapping("countries")
  public ResponseEntity<BookDto<CountryDto>> getCountries(
    @Parameter(hidden = true) final Pageable pageable
    ) {
    final int page = pageable.getPageNumber();
    final int size = pageable.getPageSize();

    return ResponseEntity.ok(new BookDto<>(
      this.countryRepository.findCountries(new BookAttributes(page, size))
        .getContent()
        .stream()
        .map(this.countryMapper::toCountryDto)
        .toArray(CountryDto[]::new),
      page,
      size
    ));
  }

  @PageableAsQueryParam
  @GetMapping("provinces")
  public ResponseEntity<BookDto<ProvinceDto>> getProvincesByCountry(
    @RequestParam(name = "country") final UUID identifier,
    @Parameter(hidden = true) final Pageable pageable
  ) {
    final int page = pageable.getPageNumber();
    final int pageSize = pageable.getPageSize();

    final var box = this.provinceRepository.findProvincesByCountryIdentifier(
      identifier,
      new BookAttributes(page, pageSize)
    );

    final var content = box.getContent()
      .stream()
      .map(this.provinceMapper::toProvinceDto)
      .toArray(ProvinceDto[]::new);

    return ResponseEntity.ok(
      new BookDto<>(content, page, pageSize)
    );
  }

  @PageableAsQueryParam
  @GetMapping("cities")
  public ResponseEntity<BookDto<CityDto>> getCities(
    @RequestParam(name = "provinceIdentifier", required = false) final UUID identifier,
    @Parameter(hidden = true) final Pageable pageable
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
        .map(this.cityMapper::toCityDto)
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
      this.cityMapper.toCityDto(result)
    );
  }
}
