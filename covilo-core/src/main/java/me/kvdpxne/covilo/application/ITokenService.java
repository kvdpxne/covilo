package me.kvdpxne.covilo.application;

import me.kvdpxne.covilo.common.exceptions.TokenExpiredException;
import me.kvdpxne.covilo.common.exceptions.TokenSignatureException;
import me.kvdpxne.covilo.domain.model.User;

public interface ITokenService {

  String createCompactRefreshToken(final User user);

  /**
   *
   */
  String createCompactAccessToken(final User user);

  String extractAudience(final String compactToken) throws TokenSignatureException, TokenExpiredException;

  String extractSubject(final String compactToken) throws TokenSignatureException, TokenExpiredException;
}
