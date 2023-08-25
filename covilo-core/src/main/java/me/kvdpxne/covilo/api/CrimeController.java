package me.kvdpxne.covilo.api;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.application.mapper.CrimeMapper;
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
@RequiredArgsConstructor
public class CrimeController {

  private final CrimeRepository crimeRepository;
  private final CrimeMapper crimeMapper;

  @GetMapping("crimes")
  public Collection<CrimeDto> getCrimes(@RequestParam UUID city) {
    return crimeRepository.findByPlace_Identifier(city, Pageable.unpaged())
      .map(crimeMapper::toResponse)
      .toList();
  }
}
