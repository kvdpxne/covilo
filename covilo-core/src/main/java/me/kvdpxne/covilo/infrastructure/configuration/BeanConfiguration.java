package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.service.AuthenticationService;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import me.kvdpxne.covilo.domain.service.CrimeService;
import me.kvdpxne.covilo.domain.service.GeolocationService;
import me.kvdpxne.covilo.domain.service.MeService;
import me.kvdpxne.covilo.domain.service.PaginationConfiguration;
import me.kvdpxne.covilo.domain.service.PasswordAuthenticator;
import me.kvdpxne.covilo.domain.service.PasswordEncodingService;
import me.kvdpxne.covilo.domain.service.SystematizationService;
import me.kvdpxne.covilo.domain.service.UserService;
import me.kvdpxne.covilo.infrastructure.security.jwt.JjwtServiceExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining Spring beans.
 * <p>
 * This class contains methods annotated with {@link Bean} to define and
 * configure beans used within the Spring application context.
 */
@Configuration
public class BeanConfiguration {

  /**
   * Creates a bean for the {@link ConfiguredPageFactory} with the provided
   * {@link PaginationConfiguration}. This bean can be used to create pages with
   * configurable pagination settings.
   *
   * @param paginationConfiguration The pagination configuration used by the
   *                                configured page factory.
   * @return A {@link ConfiguredPageFactory} instance.
   */
  @Bean
  public ConfiguredPageFactory configuredPageFactory(
    final PaginationConfiguration paginationConfiguration
  ) {
    return new ConfiguredPageFactory(
      paginationConfiguration
    );
  }

  /**
   * Configures and provides a {@link UserService} instance.
   *
   * @param userRepository          The repository for user data.
   * @param passwordEncodingService The service for encoding passwords.
   * @return A {@link UserService} instance.
   */
  @Bean
  public UserService userService(
    final UserRepository userRepository,
    final PasswordEncodingService passwordEncodingService
  ) {
    return new UserService(
      userRepository,
      passwordEncodingService
    );
  }

  /**
   * Creates and configures a {@link MeService} bean.
   *
   * @param userService The service for user-related operations.
   * @return A configured instance of {@link MeService}.
   */
  @Bean
  public MeService meService(
    final UserService userService
  ) {
    return new MeService(
      userService
    );
  }

  /**
   * Creates and configures an instance of the {@link AuthenticationService}
   * class.
   * <p>
   * This method creates a new instance of the {@link AuthenticationService}
   * class, providing it with the required dependencies such as UserService,
   * PasswordAuthenticator, and JwtService.
   *
   * @param userService           The {@link UserService} instance for managing
   *                              user-related operations.
   * @param passwordAuthenticator The {@link PasswordAuthenticator} instance for
   *                              authenticating users.
   * @param tokenService          The {@link JjwtServiceExtension} instance for
   *                              managing JWT tokens.
   * @return An instance of the {@link AuthenticationService} class configured
   * with the provided dependencies.
   */
  @Bean
  public AuthenticationService getUserAuthenticationService(
    final UserService userService,
    final PasswordAuthenticator passwordAuthenticator,
    final JjwtServiceExtension tokenService
  ) {
    return new AuthenticationService(
      userService,
      passwordAuthenticator,
      tokenService
    );
  }

  /**
   * Configures and creates an instance of the {@link SystematizationService}
   * class.
   * <p>
   * The {@link SystematizationService} class provides functionality related to
   * managing the systematization of crimes, including operations such as
   * classifying crimes into categories and classifications.
   *
   * @param classificationRepository The {@link ClassificationRepository}
   *                                 instance used for accessing classification
   *                                 data.
   * @param categoryRepository       The {@link CategoryRepository} instance
   *                                 used for accessing category data.
   * @return A configured instance of the {@link SystematizationService} class.
   */
  @Bean
  public SystematizationService systematizationService(
    final ClassificationRepository classificationRepository,
    final CategoryRepository categoryRepository
  ) {
    return new SystematizationService(
      classificationRepository,
      categoryRepository
    );
  }

  @Bean
  public GeolocationService geolocationService() {
    return new GeolocationService();
  }

  /**
   * Configures and creates an instance of the {@link CrimeService} class.
   * <p>
   * The {@link CrimeService} class provides functionality related to managing
   * crimes, including operations such as creating, updating, and deleting crime
   * records.
   *
   * @param userService            The {@link UserService} instance used for
   *                               user-related operations.
   * @param systematizationService The {@link SystematizationService} instance
   *                               used for systematization-related operations.
   * @param crimeRepository        The {@link CrimeRepository} instance used for
   *                               accessing crime data in the database.
   * @return A configured instance of the {@link CrimeService} class.
   */
  @Bean
  public CrimeService crimeService(
    final UserService userService,
    final SystematizationService systematizationService,
    final CrimeRepository crimeRepository
  ) {
    return new CrimeService(
      systematizationService,
      userService,
      crimeRepository
    );
  }
}
