package me.kvdpxne.covilo.application;

public interface IPasswordEncodingUseCase {

  /**
   * Encodes the given raw currentPassword.
   */
  String encode(final String password);
}
