package me.kvdpxne.covilo.common.exceptions;

import java.util.UUID;

public class CrimeAlreadyExistsException extends CrimeException {

  public CrimeAlreadyExistsException() {
    super("");
  }

  public CrimeAlreadyExistsException(String message) {
    super(message);
  }

  public static CrimeAlreadyExistsException byIdentifier(final UUID identifier) {
    throw new CrimeAlreadyExistsException(
      String.format(
        "the crime model with the identifier \"%s\" already exists.",
        identifier
      )
    );
  }
}
