package me.kvdpxne.covilo.domain.service;

/**
 * Interface for authenticating users based on their credentials.
 */
public interface PasswordAuthenticator {

  /**
   * Authenticates a user based on the provided name and password.
   *
   * @param name     The name or identifier of the user.
   * @param password The password associated with the user.
   */
  void authenticate(
    final String name,
    final String password
  );
}
