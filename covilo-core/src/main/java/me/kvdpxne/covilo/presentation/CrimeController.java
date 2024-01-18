package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.ICrimeLifecycleService;
import me.kvdpxne.covilo.application.dto.BookDto;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.application.mapper.ICrimeMapper;
import me.kvdpxne.covilo.application.payload.ReportCrimeRequest;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
@RequestMapping("/api/0.1.0")
@RequiredArgsConstructor
public class CrimeController {

  private final CrimeRepository crimeRepository;
  private final ICrimeLifecycleService crimeLifecycleService;
  private final ICrimeMapper crimeMapper;

  @PageableAsQueryParam
  @GetMapping("crimes")
  public ResponseEntity<BookDto<CrimeDto>> getCrimes(
    @RequestParam(required = false) final UUID city,
    @Parameter(hidden = true) final Pageable pageable
  ) {
    final int page = pageable.getPageNumber();
    final int pageSize = pageable.getPageSize();

    final BookAttributes attributes = new BookAttributes(page, pageSize);
    final Book<Crime> book = null == city
      ? this.crimeRepository.findCrimes(attributes)
      : this.crimeRepository.findCrimesByCityIdentifier(city, attributes);

    return ResponseEntity.ok(new BookDto<>(
      book.getContent()
        .stream()
        .map(this.crimeMapper::toCrimeDto)
        .toArray(CrimeDto[]::new),
      page,
      pageSize
    ));
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
      this.crimeMapper.toCrimeDto(crime)
    );
  }

  @PostMapping("crime/report")
  public ResponseEntity<CrimeDto> reportCrime(
    @RequestBody ReportCrimeRequest request
  ) {
    Crime crime = this.crimeMapper.toCrime(request);

    try {
      crime = this.crimeLifecycleService.createCrime(crime);
    } catch (final CrimeAlreadyExistsException exception) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.ok(
      this.crimeMapper.toCrimeDto(crime)
    );
  }
}
