package me.kvdpxne.covilo.infrastructure.configuration;

import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.service.UserLifecycleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

  @Bean
  public UserLifecycleService userLifecycleService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
    return new UserLifecycleService(userRepository, passwordEncoder);
  }
}
