package me.kvdpxne.covilo.infrastructure.security.jwt;

import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.OctetPrivateJwk;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class is used to manage and store key pairs used for JSON Web Tokens
 * (JWT).
 *
 * @since 0.1.0
 */
@Slf4j
@Service
public class KeyService {

  /**
   *
   */
  public OctetPrivateJwk<PrivateKey, PublicKey> _buildPrivateJwk(
    final Key privateKey
  ) {
    return Jwks.builder()
      .octetKey((PrivateKey) privateKey)
      .idFromThumbprint()
      .build();
  }

  public Path toPath(
    final String keyIdentifier
  ) {
    return Path.of(STR."\{keyIdentifier}.key");
  }

  public Key loadKey(
    final Path path,
    final KeyAlgorithm keyAlgorithm
  ) {
    final byte[] bytes;
    try (final InputStream input = Files.newInputStream(path)) {
      final int size = input.available();
      if (0 == size) {
        throw new KeyFileAccessException(
          ""
        );
      }

      bytes = new byte[size];
      if (-1 == input.read(bytes, 0, bytes.length)) {
        // TODO handling needed
      }
    } catch (final IOException exception) {
      throw new KeyFileException(
        "",
        exception
      );
    }

    final PKCS8EncodedKeySpec keySpecification = new PKCS8EncodedKeySpec(bytes);
    final KeyFactory keyFactory = keyAlgorithm.getKeyFactory();

    try {
      return keyFactory.generatePrivate(keySpecification);
    } catch (final InvalidKeySpecException exception) {
      throw new KeyException(
        "",
        exception
      );
    }
  }

  /**
   *
   */
//  @Caching(
//    evict = {
//      @CacheEvict(
//        cacheNames = "jjwt_key",
//        key = "keyIdentifier"
//      )
//    }
//  )
  public Key loadKey(
    final String keyIdentifier,
    final KeyAlgorithm algorithm
  ) {
    final Path path = this.toPath(keyIdentifier);
    if (!Files.exists(path)) {
      throw new KeyFileAccessException(
        STR."Key file does not exist: \{path}."
      );
    }

    return this.loadKey(path, algorithm);
  }
}
