package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.application.PasswordEncodingUseCase;
import me.kvdpxne.covilo.application.UserLifecycleUseCase;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.infrastructure.jpa.repository.CrimeDao;
import me.kvdpxne.covilo.infrastructure.jpa.repository.TokenDao;
import me.kvdpxne.covilo.domain.service.CrimeLifecycleService;
import me.kvdpxne.covilo.domain.service.UserAuthenticationService;
import me.kvdpxne.covilo.domain.service.UserLifecycleService;
import me.kvdpxne.covilo.infrastructure.jwt.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class BeanConfiguration {

  @Bean
  public CrimeLifecycleService crimeLifecycleService(
    final CrimeDao crimeRepository
  ) {
    return new CrimeLifecycleService(crimeRepository);
  }

  @Bean
  public UserLifecycleService userLifecycleService(
    final UserRepository userRepository,
    final PasswordEncodingUseCase passwordEncodingUseCase
  ) {
    return new UserLifecycleService(userRepository, passwordEncodingUseCase);
  }

  @Bean
  public UserAuthenticationService userAuthenticationService(
    final UserRepository userRepository,
    final UserLifecycleUseCase  userLifecycleUseCase,
    final TokenDao tokenRepository,
    final TokenService          tokenService,
    final AuthenticationManager authenticationManager
  ) {
    return new UserAuthenticationService(
      userRepository,
      userLifecycleUseCase,
      tokenRepository,
      tokenService,
      authenticationManager
    );
  }
}
