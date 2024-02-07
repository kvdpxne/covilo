package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.domain.persistence.AdministrativeDivisionRepository;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.domain.persistence.CityRepository;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.domain.persistence.CountryRepository;
import me.kvdpxne.covilo.domain.persistence.RegionRepository;
import me.kvdpxne.covilo.domain.port.out.GeolocationServicePort;
import me.kvdpxne.covilo.domain.port.out.ITokenService;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.port.out.UserPasswordAuthenticationTokenPort;
import me.kvdpxne.covilo.domain.port.out.UserPasswordEncodePort;
import me.kvdpxne.covilo.domain.port.out.UserPasswordMatchesPort;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import me.kvdpxne.covilo.domain.port.out.UserValidatorPort;
import me.kvdpxne.covilo.domain.service.CrimeLifecycleService;
import me.kvdpxne.covilo.domain.service.GeolocationService;
import me.kvdpxne.covilo.domain.service.SystematizationService;
import me.kvdpxne.covilo.domain.service.SystematizationService2;
import me.kvdpxne.covilo.domain.service.UserAuthenticationService;
import me.kvdpxne.covilo.domain.service.UserMeService;
import me.kvdpxne.covilo.domain.service.UserService;
import me.kvdpxne.covilo.domain.service.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public CrimeLifecycleService crimeLifecycleService(
    final UserServicePort userService,
    final SystematizationService2 systematizationService,
    final GeolocationServicePort geolocationService,
    final CrimeRepository crimeRepository
  ) {
    return new CrimeLifecycleService(
      userService,
      systematizationService,
      geolocationService,
      crimeRepository
    );
  }

  @Bean
  public GeolocationService geolocationService(
    final AdministrativeDivisionRepository administrativeDivisionRepository,
    final CityRepository cityRepository,
    final CountryRepository countryRepository,
    final RegionRepository regionRepository
  ) {
    return new GeolocationService(
      administrativeDivisionRepository,
      cityRepository,
      countryRepository,
      regionRepository
    );
  }

  @Bean
  public SystematizationService getSystematizationService(
    final ClassificationRepository classificationRepository,
    final CategoryRepository categoryRepository
    ) {
    return new SystematizationService(
      classificationRepository,
      categoryRepository
    );
  }

  @Bean
  public UserAuthenticationService getUserAuthenticationService(
    final UserServicePort userService,
    final UserPasswordAuthenticationTokenPort userPasswordAuthenticationToken,
    final ITokenService tokenService,
    final TokenRepository tokenRepository
    ) {
    return new UserAuthenticationService(
      userService,
      userPasswordAuthenticationToken,
      tokenService,
      tokenRepository
    );
  }

  /**
   *
   */
  @Bean
  public UserMeService getUserMeService(
    final UserServicePort userServicePort,
    final UserPasswordMatchesPort userPasswordMatchesPort
  ) {
    return new UserMeService(
      userServicePort,
      userPasswordMatchesPort
    );
  }

  @Bean
  public UserService getUserService(
    final UserRepository userRepository,
    final UserPasswordEncodePort passwordEncodingUseCase,
    final UserValidatorPort userValidatorService
  ) {
    return new UserService(
      userRepository,
      passwordEncodingUseCase,
      userValidatorService
    );
  }

  @Bean
  public UserValidator userValidatorService() {
    return new UserValidator();
  }
}
