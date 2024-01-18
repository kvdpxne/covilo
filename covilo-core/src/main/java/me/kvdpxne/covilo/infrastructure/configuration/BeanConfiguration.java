package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.application.ITokenService;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.port.out.UserPasswordEncodePort;
import me.kvdpxne.covilo.domain.port.out.UserPasswordMatchesPort;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import me.kvdpxne.covilo.domain.port.out.UserValidatorPort;
import me.kvdpxne.covilo.domain.service.CrimeLifecycleService;
import me.kvdpxne.covilo.domain.service.UserAuthenticationService;
import me.kvdpxne.covilo.domain.service.UserMeService;
import me.kvdpxne.covilo.domain.service.UserService;
import me.kvdpxne.covilo.domain.service.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class BeanConfiguration {

  @Bean
  public CrimeLifecycleService crimeLifecycleService(
    final CrimeRepository crimeRepository
  ) {
    return new CrimeLifecycleService(crimeRepository);
  }

  @Bean
  public UserAuthenticationService userAuthenticationService2(
    final UserServicePort userLifecycleUseCase,
    final ITokenService tokenLifecycleUserCase,
    final TokenRepository tokenRepository,
    final AuthenticationManager authenticationManager
  ) {
    return new UserAuthenticationService(
      userLifecycleUseCase,
      tokenLifecycleUserCase,
      tokenRepository,
      authenticationManager
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
