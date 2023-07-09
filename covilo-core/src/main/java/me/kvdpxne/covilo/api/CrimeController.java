package me.kvdpxne.covilo.api;

import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/0.1.0", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrimeController {

  private final CrimeRepository crimeRepository;

  public CrimeController(CrimeRepository crimeRepository) {
    this.crimeRepository = crimeRepository;
  }

  @GetMapping("crimes")
  public Collection<CrimeDto> getCrimes(
    @RequestParam UUID city
  ) {
    return crimeRepository.findByPlace_Identifier(city, Pageable.unpaged()).map(
      it -> new CrimeDto(
        it.getIdentifier(),
        it.getDescription(),
        it.isConfirmed(),
        it.getClassifications().stream().map(it2 -> new CrimeClassificationDto(
          it2.getIdentifier(),
          it2.getName()
        )).toList(),
        new UserDto(
          it.getReporter().getIdentifier(),
          it.getReporter().getEmail(),
          it.getReporter().getEmail(),
          it.getReporter().getGender(),
          it.getReporter().getBirthDate()
        ),
        new CityDto(
          it.getPlace().getIdentifier(),
          it.getPlace().getName(),
          it.getPlace().getNationalName(),
          it.getPlace().getCapitalType(),
          it.getPlace().getPopulation()
        )
      )
    ).toList();
  }
}
