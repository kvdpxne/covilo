package me.kvdpxne.covilo.domain.model;

import me.kvdpxne.covilo.domain.aggregation.Buildable;

/**
 * Represents a pair of access and refresh tokens.
 */
public final class TokenPair {

  private final String accessToken;
  private final String refreshToken;
  private final long expiry;
  private final TokenType tokenType;

  /**
   * Constructs a new {@link TokenPair} instance with the provided access and
   * refresh tokens.
   * <p>
   * It's recommended to use the {@link #builder()} method to construct a
   * {@link TokenPair} object instead of using this constructor directly.
   *
   * @param accessToken  The access token.
   * @param refreshToken The refresh token.
   */
  public TokenPair(
    final String accessToken,
    final String refreshToken,
    final long expiry,
    final TokenType tokenType
  ) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.expiry = expiry;
    this.tokenType = tokenType;
  }

  /**
   * Builder pattern implementation for constructing {@link TokenPair} objects.
   */
  public static final class TokenPairBuilder
    implements Buildable<TokenPair> {

    private String accessToken;
    private String refreshToken;
    private long expiry;
    private TokenType tokenType;

    /**
     * Constructs a new {@link TokenPairBuilder} initialized with the provided
     * access and refresh tokens.
     *
     * @param accessToken  The access token.
     * @param refreshToken The refresh token.
     */
    private TokenPairBuilder(
      final String accessToken,
      final String refreshToken
    ) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
    }

    /**
     * Default constructor for {@link TokenPairBuilder}.
     */
    private TokenPairBuilder() {}

    /**
     * Sets the access token for the {@link TokenPair} being built.
     *
     * @param accessToken The access token.
     * @return This {@link TokenPairBuilder} instance.
     */
    public TokenPairBuilder withAccessToken(
      final String accessToken
    ) {
      this.accessToken = accessToken;
      return this;
    }

    /**
     * Sets the refresh token for the {@link TokenPair} being built.
     *
     * @param refreshToken The refresh token.
     * @return This {@link TokenPairBuilder} instance.
     */
    public TokenPairBuilder withRefreshToken(
      final String refreshToken
    ) {
      this.refreshToken = refreshToken;
      return this;
    }

    public TokenPairBuilder withExpiry(
      final long expiry
    ) {
      this.expiry = expiry;
      return this;
    }

    public TokenPairBuilder withTokenType(
      final TokenType tokenType
    ) {
      this.tokenType = tokenType;
      return this;
    }

    /**
     * Builds and returns the TokenPair object.
     *
     * @return A new {@link TokenPair} instance.
     */
    @Override
    public TokenPair build() {
      return new TokenPair(
        this.accessToken,
        this.refreshToken,
        this.expiry,
        this.tokenType
      );
    }
  }

  /**
   * Returns a new {@link TokenPairBuilder} instance to build a
   * {@link TokenPair} object.
   *
   * @return A {@link TokenPairBuilder} instance.
   */
  public static TokenPairBuilder builder() {
    return new TokenPairBuilder();
  }

  /**
   * Returns a new {@link TokenPairBuilder} instance initialized with the values
   * from this {@link TokenPair} object.
   *
   * @return A {@link TokenPairBuilder} instance.
   */
  public TokenPairBuilder toBuilder() {
    return new TokenPairBuilder(
      this.accessToken,
      this.refreshToken
    );
  }

  /**
   * Retrieves the access token.
   *
   * @return The access token.
   */
  public String getAccessToken() {
    return this.accessToken;
  }

  /**
   * Retrieves the refresh token.
   *
   * @return The refresh token.
   */
  public String getRefreshToken() {
    return this.refreshToken;
  }

  public long getExpiry() {
    return this.expiry;
  }

  public TokenType getTokenType() {
    return this.tokenType;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o The reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument;
   * {@code false} otherwise.
   */
  @Override
  public boolean equals(
    final Object o
  ) {
    if (this == o) {
      return true;
    }
    if (null == o || this.getClass() != o.getClass()) {
      return false;
    }
    final var that = (TokenPair) o;
    return this.accessToken.equals(that.accessToken) &&
      this.refreshToken.equals(that.refreshToken);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    int result = this.accessToken.hashCode();
    result = 31 * result + this.refreshToken.hashCode();
    return result;
  }

  /**
   * Returns a string representation of the {@link TokenPair} object.
   *
   * @return A string representation of the {@link TokenPair} object.
   */
  @Override
  public String toString() {
    return STR."""
      TokenPair{
        accessToken="\{accessToken}",
        refreshToken="\{refreshToken}"
      }""";
  }
}
