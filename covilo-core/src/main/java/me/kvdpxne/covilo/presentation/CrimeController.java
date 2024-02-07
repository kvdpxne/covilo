package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.port.out.CrimeService;
import me.kvdpxne.covilo.infrastructure.swagger.HiddenParameter;
import me.kvdpxne.covilo.infrastructure.swagger.PagingAsQueryParameter;
import me.kvdpxne.covilo.presentation.dto.CrimeDto;
import me.kvdpxne.covilo.presentation.mappers.CrimeMapper;
import me.kvdpxne.covilo.presentation.payloads.ReportCrimeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = Endpoints.CRIME)
@RestController
public class CrimeController {

  /**
   *
   */
  private final CrimeService crimeService;

  /**
   *
   */
  private final CrimeMapper crimeMapper;

  @Operation(summary = "Retrieve crimes with paging support.")
  @PagingAsQueryParameter
  @GetMapping("all")
  public Page<CrimeDto> getCrimes(
    @HiddenParameter
    final CrimeSearchQuery query,
    @HiddenParameter
    final PageRange page
  ) {
    final var criteria = this.crimeMapper.toCrimeSearchCriteria(query);

    // Retrieves crimes based on provided search criteria and paging
    // information.
    final var crimes = (Page<Crime>) this.crimeService.getCrimes(criteria, page);

    // Maps Crime objects to CrimeDto objects for presentation.
    return crimes.map(this.crimeMapper::toDto);
  }

  @Operation(summary = "Retrieve Crime by identifier.")
  @ApiResponse(
    responseCode = "200",
    description = "Found the crime.",
    content = @Content(
      mediaType = "application/json",
      schema = @Schema(
        implementation = CrimeDto.class
      )
    )
  )
  @GetMapping("{identifier}")
  public CrimeDto getCrimeByIdentifier(
    @PathVariable
    final UUID identifier
  ) {
    // Retrieves a Crime object from the service layer based on provided
    // identifier.
    final var crime = this.crimeService.getCrimeByIdentifier(identifier);

    // Maps the Crime object to CrimeDto for presentation.
    return this.crimeMapper.toDto(crime);
  }

  @PostMapping("report")
  public void reportCrime(
    @RequestBody
    final ReportCrimeRequest request
  ) {
    this.crimeService.createCrime(
      this.crimeMapper.fromCreateRequest(request)
    );
  }

  @GetMapping("count")
  public long countCrimes() {
    return this.crimeService.countCrimes();
  }
}
