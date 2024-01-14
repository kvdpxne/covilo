package me.kvdpxne.covilo.common.exceptions;

public class UserAlreadyExistsException extends UserException {

  private UserAlreadyExistsException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }

  public static void byIdentifier(
    final Object identifier
  ) throws UserAlreadyExistsException {
    throw new UserAlreadyExistsException(
      "User with identifier \"%s\" already exists.",
      identifier
    );
  }

  /**
   *
   */
  public static UserAlreadyExistsException byEmail(
    final String email
  ) throws UserAlreadyExistsException {
    throw new UserAlreadyExistsException(
      "User with email \"%s\" already exists.",
      email
    );
  }
}
