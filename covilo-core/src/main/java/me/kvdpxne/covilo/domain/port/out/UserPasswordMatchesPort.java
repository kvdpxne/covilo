package me.kvdpxne.covilo.domain.port.out;

public interface UserPasswordMatchesPort {

  /**
   *
   */
  boolean matches(final String rawPassword, final String encodedPassword);
}
