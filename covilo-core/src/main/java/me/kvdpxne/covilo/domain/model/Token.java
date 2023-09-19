package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record Token(
  UUID identifier,
  String token,
  TokenType tokenType,
  User user,
  boolean revoked,
  boolean expired
) {

  public Builder toBuilder() {
    return new Builder(
      this.identifier,
      this.token,
      this.tokenType,
      this.user,
      this.revoked,
      this.expired
    );
  }

  public static final class Builder
    implements IBuilder<Token> {

    private UUID identifier;
    private String token;
    private TokenType tokenType;
    private User user;
    private boolean revoked;
    private boolean expired;

    private Builder(
      final UUID identifier,
      final String token,
      final TokenType tokenType,
      final User user,
      final boolean revoked,
      final boolean expired
    ) {
      this.identifier = identifier;
      this.token = token;
      this.tokenType = tokenType;
      this.user = user;
      this.revoked = revoked;
      this.expired = expired;
    }

    public Builder() {
      super();
    }

    public Builder identifier(final UUID identifier) {
      this.identifier = identifier;
      return this;
    }

    public Builder token(final String token) {
      this.token = token;
      return this;
    }

    public Builder tokenType(final TokenType tokenType) {
      this.tokenType = tokenType;
      return this;
    }

    public Builder user(final User user) {
      this.user = user;
      return this;
    }

    public Builder revoked(final boolean revoked) {
      this.revoked = revoked;
      return this;
    }

    public Builder expired(final boolean expired) {
      this.expired = expired;
      return this;
    }

    @Override
    public Token build() {
      if (null == this.identifier) {
        this.identifier = UUID.randomUUID();
      }

      return new Token(
        this.identifier,
        this.token,
        this.tokenType,
        this.user,
        this.revoked,
        this.expired
      );
    }
  }
}
