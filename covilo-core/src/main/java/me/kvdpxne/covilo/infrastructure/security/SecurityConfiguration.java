package me.kvdpxne.covilo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecurityConfiguration {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  private final TokenAuthenticationRequestFilter authenticationRequestFilter;
  private final LogoutHandler logoutHandler;
  private final CorsConfigurationSource corsConfigurationSource;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    return security
      .cors(configurer -> configurer.configurationSource(this.corsConfigurationSource))
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(configurer -> {
        configurer.requestMatchers(
          "/api/0.1.0/geographical/**",
          "/api/0.1.0/auth/**",

          "/v2/api-docs",
          "/v3/api-docs",
          "/v3/api-docs/**",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui/**",
          "/webjars/**",
          "/swagger-ui.html"
        ).permitAll();

        configurer.requestMatchers("/api/v1/test/**").hasAnyRole(
          Role.ADMINISTRATOR.name(),
          Role.MODERATOR.name(),
          Role.EDITOR.name()
        );

        configurer.anyRequest().authenticated();
      })
      .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(this.authenticationProvider())
      .addFilterBefore(this.authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class)
      .logout(configurer -> {
        configurer.logoutUrl("/api/v1/auth/logout");
        configurer.addLogoutHandler(this.logoutHandler);
        configurer.logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));
      })
      .build();
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    var provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(this.userDetailsService);
    provider.setPasswordEncoder(this.passwordEncoder);
    return provider;
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}
