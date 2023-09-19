package me.kvdpxne.covilo.application;

public interface IPasswordEncodingUseCase {

  /**
   * Encodes the given raw password.
   */
  String encode(final String password);
}
