package me.kvdpxne.covilo.api;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/0.1.0/geographical", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GeographicalController {

  private final CountryRepository countryRepository;

  @GetMapping(path = "/countries")
  public ResponseEntity<Collection<Country>> getCountries() {
    return ResponseEntity.ok(countryRepository.findAll());
  }
}
