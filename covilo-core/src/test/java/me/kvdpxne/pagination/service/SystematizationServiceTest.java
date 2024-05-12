package me.kvdpxne.pagination.service;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.service.SystematizationService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SystematizationServiceTest {

  @Autowired
  private SystematizationService systematizationService;

  /**
   *
   */
  @Test
  @Order(0)
  void test_000() {
    var it = Classification.builder()
      .withIdentifier(UUID.fromString("2d07a0d8-0d34-4624-af6d-b584b46181c8"))
      .withName("TEST_CLASSIFICATION")
      .build();

    this.systematizationService.createClassification(it);
  }

  @Test
  @Order(1)
  void test_001() {
    System.out.println(
      this.systematizationService.getClassificationByIdentifier(
        UUID.fromString("2d07a0d8-0d34-4624-af6d-b584b46181c8")
      )
    );
  }
}
