package me.kvdpxne.covilo.infrastructure.security.jwt;

import io.jsonwebtoken.LocatorAdapter;
import io.jsonwebtoken.ProtectedHeader;
import java.security.Key;
import java.security.PrivateKey;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Adapter for locating cryptographic keys based on information in JWT headers.
 *
 * @since 0.1.0
 */
@Component
public class JjwtKeyLocatorAdapter
  extends LocatorAdapter<Key> {

  /**
   * The service for managing cryptographic keys.
   */
  private final KeyService keyService;

  /**
   * Constructs a new {@link JjwtKeyLocatorAdapter} with the specified
   * {@link KeyService}.
   *
   * @param keyService The service for managing cryptographic keys.
   */
  @Autowired
  public JjwtKeyLocatorAdapter(
    final KeyService keyService
  ) {
    this.keyService = keyService;
  }

  /**
   * Locates and loads a cryptographic key based on information in the JWT
   * header.
   *
   * @param header The protected header containing information from the JWT
   *               header.
   * @return The cryptographic key associated with the JWT header.
   * @throws JwtException                     If the key identifier or algorithm
   *                                          in the JWT header is invalid.
   * @throws JwtUnsupportedAlgorithmException If the algorithm specified in the
   *                                          JWT header is not supported.
   */
  @Override
  public Key locate(
    final ProtectedHeader header
  ) {
    // Retrieve the Key Identifier (kid) from the JWT header.
    final String keyIdentifier = header.getKeyId();

    // Check if the key identifier in the JWT header is null or empty. If
    // so, throw an exception indicating an invalid key identifier.
    if (null == keyIdentifier || keyIdentifier.isBlank()) {
      throw new JwtException("Invalid key identifier");
    }

    // Retrieve the algorithm used for signing the JWT from the header.
    final String algorithm = keyIdentifier.substring(
      0, keyIdentifier.indexOf('_')
    );

    // Check if the algorithm in the JWT header is null or empty. If so,
    // throw an exception indicating an invalid algorithm.
    if (algorithm.isBlank()) {
      throw new JwtException("Invalid algorithm");
    }

    // Map the algorithm from the JWT header to a KeyAlgorithm enum value,
    // throwing an exception if the algorithm is not supported.
    final KeyAlgorithm keyAlgorithm = Arrays.stream(KeyAlgorithm.values())
      .filter(it -> it.getAlgorithm().equalsIgnoreCase(algorithm))
      .findFirst()
      .orElseThrow(() -> new JwtUnsupportedAlgorithmException(
        STR."Not supported algorithm: \{algorithm}"
      ));

    // Load the key associated with the given key identifier and algorithm
    // using the key service.
    final Key key = this.keyService.loadKey(keyIdentifier, keyAlgorithm);
    return this.keyService._buildPrivateJwk(
      key
    ).toKeyPair().getPublic();
  }
}
