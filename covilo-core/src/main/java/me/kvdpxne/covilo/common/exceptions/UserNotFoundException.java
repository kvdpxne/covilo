package me.kvdpxne.covilo.common.exceptions;

public class UserNotFoundException extends UserException {

  public UserNotFoundException() {
  }

  public UserNotFoundException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }

  public static UserNotFoundException byIdentifier(
    final Object identifier
  ) throws UserNotFoundException {
    throw new UserNotFoundException(
      "User with identifier \"%s\" does not exist.",
      identifier
    );
  }

  public static void byEmail(
    final String email
  ) throws UserNotFoundException {
    throw new UserNotFoundException(
      "User with email address \"%s\" does not exist.",
      email
    );
  }
}
