package me.kvdpxne.covilo.domain.services;

import me.kvdpxne.covilo.domain.service.SystematizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 */
@SpringBootTest
final class SystematizationServiceTest {

  /**
   *
   */
  private final SystematizationService systematizationService;

  /**
   *
   */
  @Autowired
  SystematizationServiceTest(
    final SystematizationService systematizationService
  ) {
    this.systematizationService = systematizationService;
  }
}
