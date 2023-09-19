package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.application.IPasswordEncodingUseCase;
import me.kvdpxne.covilo.application.ITokenService;
import me.kvdpxne.covilo.application.IUserLifecycleService;
import me.kvdpxne.covilo.domain.persistence.ICrimeRepository;
import me.kvdpxne.covilo.domain.persistence.ITokenRepository;
import me.kvdpxne.covilo.domain.persistence.IUserRepository;
import me.kvdpxne.covilo.domain.service.CrimeLifecycleService;
import me.kvdpxne.covilo.domain.service.UserAuthenticationService;
import me.kvdpxne.covilo.domain.service.UserLifecycleService;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ICrimeJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class BeanConfiguration {

  @Bean
  public CrimeLifecycleService crimeLifecycleService(
    final ICrimeRepository crimeRepository
  ) {
    return new CrimeLifecycleService(crimeRepository);
  }

  @Bean
  public UserAuthenticationService userAuthenticationService2(
    final IUserLifecycleService userLifecycleUseCase,
    final ITokenService tokenLifecycleUserCase,
    final ITokenRepository tokenRepository,
    final AuthenticationManager authenticationManager
  ) {
    return new UserAuthenticationService(
      userLifecycleUseCase,
      tokenLifecycleUserCase,
      tokenRepository,
      authenticationManager
    );
  }

  @Bean
  public UserLifecycleService userLifecycleService(
    final IUserRepository userRepository,
    final IPasswordEncodingUseCase passwordEncodingUseCase
  ) {
    return new UserLifecycleService(userRepository, passwordEncodingUseCase);
  }
}
