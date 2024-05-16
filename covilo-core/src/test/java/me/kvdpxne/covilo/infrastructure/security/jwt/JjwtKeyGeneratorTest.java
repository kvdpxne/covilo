package me.kvdpxne.covilo.infrastructure.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class JjwtKeyGeneratorTest {

  @Autowired
  private JjwtKeyGenerator jjwtKeyGenerator;

  @Test
  void generateKeys() {
    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.X25519,
      "x25519_05_2024"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.X25519,
      "x25519_05_2024_refresh"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.X448,
      "x448_05_2024"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.X448,
      "x448_05_2024_refresh"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.Ed25519,
      "ed25519_05_2024"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.Ed25519,
      "ed25519_05_2024_refresh"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.Ed448,
      "ed448_05_2024"
    );

    this.jjwtKeyGenerator.generateKeyAndStore(
      KeyAlgorithm.Ed448,
      "ed448_05_2024_refresh"
    );
  }
}
