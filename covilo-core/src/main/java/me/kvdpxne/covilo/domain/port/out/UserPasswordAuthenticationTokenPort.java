package me.kvdpxne.covilo.domain.port.out;

public interface UserPasswordAuthenticationTokenPort {

  void authenticate(final String email, final String password);
}
