package me.kvdpxne.covilo.domain.model;

/**
 * Enumerates the types of JWT tokens.
 */
public enum TokenType {

  BEARER,

  /**
   * Represents an access token, which is used to authenticate and authorize
   * users for accessing protected resources.
   */
  @Deprecated
  ACCESS,

  /**
   * Represents a refresh token, which is used to obtain a new access token
   * after the previous one has expired.
   */
  @Deprecated
  REFRESH
}
