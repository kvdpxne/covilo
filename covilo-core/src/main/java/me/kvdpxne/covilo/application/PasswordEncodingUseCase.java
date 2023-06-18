package me.kvdpxne.covilo.application;

public interface PasswordEncodingUseCase {

  /**
   * Encodes the given raw password.
   */
  String encode(final String password);
}
