package me.kvdpxne.covilo.api;

import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.payload.ReportCrimeRequest;
import me.kvdpxne.covilo.application.CrimeLifecycleUseCase;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.application.mapper.CrimeMapper;
import me.kvdpxne.covilo.domain.exception.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.CrimeNotFoundException;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CrimeDao;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/0.1.0")
@RequiredArgsConstructor
public class CrimeController {

  private final CrimeLifecycleUseCase crimeLifecycleUseCase;
  private final CrimeDao crimeRepository;
  private final CrimeMapper crimeMapper;

  @GetMapping("/crimes")
  public Collection<CrimeDto> getCrimes(@RequestParam UUID city) {
    return crimeRepository.findByPlace_Identifier(city, Pageable.unpaged())
      .map(crimeMapper::toDto)
      .toList();
  }

  @GetMapping("/crime/{identifier}")
  public ResponseEntity<CrimeDto> getCrimeByIdentifier(@PathVariable final UUID identifier) {
    final CrimeEntity crime;

    try {
      crime = this.crimeLifecycleUseCase.getCrimeByIdentifier(identifier);
    } catch (final CrimeNotFoundException exception) {
      return ResponseEntity.notFound().build();
    }

    final CrimeDto crimeDto = this.crimeMapper.toDto(crime);
    return ResponseEntity.ok(crimeDto);
  }

  @PostMapping("/crime/report")
  public ResponseEntity<CrimeDto> reportCrime(@RequestBody ReportCrimeRequest request) {
    CrimeEntity crime = this.crimeMapper.toDomain(request);

    try {
      crime = this.crimeLifecycleUseCase.createCrime(crime);
    } catch (final CrimeAlreadyExistsException exception) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    final CrimeDto crimeDto = this.crimeMapper.toDto(crime);
    return ResponseEntity.ok(crimeDto);
  }
}
