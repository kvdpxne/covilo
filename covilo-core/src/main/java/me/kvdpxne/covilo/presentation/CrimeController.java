package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.service.CrimeService;
import me.kvdpxne.covilo.domain.service.GeolocationService;
import me.kvdpxne.covilo.domain.service.SystematizationService;
import me.kvdpxne.covilo.domain.service.UserService;
import me.kvdpxne.covilo.presentation.dto.CrimeDto;
import me.kvdpxne.covilo.presentation.payloads.CreateCrimeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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


  private final SystematizationService systematizationService;

  private final GeolocationService geolocationService;

  private final UserService userService;

//  @Operation(
//    summary = "Retrieve Crimes",
//    description = "Retrieve a page of crimes based on provided search criteria with paging."
//  )
//  @PagingAsQueryParameter
//  @GetMapping("all")
//  public Page<CrimeDto> getCrimes(
//    @HiddenParameter
//    final CrimeSearchQuery query,
//    @HiddenParameter
//    final PageRange page
//  ) {
//    final var criteria = this.crimeMapper.toCrimeSearchCriteria(query);
//
//    // Retrieves crimes based on provided search criteria and paging
//    // information.
//    final var crimes = (Page<Crime>) this.crimeService.getCrimes(criteria, page);
//
//    // Maps Crime objects to CrimeDto objects for presentation.
//    return crimes.map(this.crimeMapper::toDto);
//  }

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
  public Crime getCrime(
    @PathVariable
    final String identifier
  ) {
    // Retrieves a Crime object from the service layer based on provided
    // identifier.
    final var crime = this.crimeService.getCrimeByIdentifier(identifier);

    // Maps the Crime object to CrimeDto for presentation.
    return crime;
  }

  /**
   * Endpoint to create a new crime based on the provided request data.
   *
   * @param request The request containing data for creating the crime.
   */
  @Operation(
    summary = "Create Crime",
    description = "Create a new crime based on the provided request data."
  )
  @PostMapping
  public void createCrime(
    @RequestBody
    final CreateCrimeRequest request
  ) {

    //
    this.crimeService.createCrime(
      request.asCrime()
    );
  }

  @Operation(
    summary = "Delete Crime",
    description = "Delete a crime based on its identifier."
  )
  @DeleteMapping("{identifier}")
  public void deleteCrime(
    @PathVariable
    final String identifier
  ) {
    this.crimeService.deleteCrimeByIdentifier(identifier);
  }

  @Operation(
    summary = "Count crimes",
    description = "Returns the total number of crimes stored in the system."
  )
  @ApiResponse(
    responseCode = "200",
    description = "Successfully retrieved the count of crimes.",
    content = @Content(
      mediaType = "application/json",
      schema = @Schema(
        type = "integer",
        description = "The total count of crimes."
      )
    )
  )
  @GetMapping("count")
  public long countCrimes() {
    // Retrieves the total count of crimes.
    return this.crimeService.countCrimes();
  }
}
