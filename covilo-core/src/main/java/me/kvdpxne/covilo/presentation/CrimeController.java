package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.port.out.ICrimeLifecycleService;
import me.kvdpxne.covilo.presentation.dto.BookDto;
import me.kvdpxne.covilo.presentation.dto.CrimeDto;
import me.kvdpxne.covilo.presentation.mappers.CrimeMapper;
import me.kvdpxne.covilo.presentation.payloads.ReportCrimeRequest;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Endpoints.CRIME)
@RequiredArgsConstructor
public class CrimeController {

  private final CrimeRepository crimeRepository;
  private final ICrimeLifecycleService crimeLifecycleService;
  private final CrimeMapper crimeMapper;

  @GetMapping(
    path = {
      "all",
      "everything"
    }
  )
  public Page<CrimeDto> getCrimes2(
    @RequestParam(required = false)
    final UUID cityIdentifier,
    @Parameter(hidden = true)
    final Pageable pageable
  ) {
    return ((Page<Crime>) this.crimeLifecycleService.getCrimes(
      PageRange.of(
        pageable.getPageNumber(),
        pageable.getPageSize()
      )
    )).map(this.crimeMapper::toDto);
  }

  @GetMapping("crime")
  public ResponseEntity<CrimeDto> getCrimeByIdentifier(
    @RequestParam final UUID identifier
  ) {
    final Crime crime;

    try {
      crime = this.crimeLifecycleService.getCrimeByIdentifier(identifier);
    } catch (final CrimeNotFoundException exception) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(
      this.crimeMapper.toDto(crime)
    );
  }

  @PostMapping("crime/report")
  public ResponseEntity<CrimeDto> reportCrime(
    @RequestBody ReportCrimeRequest request
  ) {
    Crime crime = this.crimeMapper.toDomain(request);

    try {
      crime = this.crimeLifecycleService.createCrime(crime);
    } catch (final CrimeAlreadyExistsException exception) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.ok(
      this.crimeMapper.toDto(crime)
    );
  }

  @GetMapping("count")
  public long countCrimes() {
    return this.crimeLifecycleService.countCrimes();
  }
}
