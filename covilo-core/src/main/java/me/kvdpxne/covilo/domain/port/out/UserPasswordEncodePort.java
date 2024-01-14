package me.kvdpxne.covilo.domain.port.out;

public interface UserPasswordEncodePort {

  /**
   * Encodes the given raw currentPassword.
   */
  String encode(final String rawPassword);
}
