package me.kvdpxne.covilo.domain.port.out;

import me.kvdpxne.covilo.common.exceptions.UserInvalidEmailAddressException;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;

public interface UserValidatorPort {

  /**
   * Checks whether the provided email address is correct and meets email
   * address standards.
   *
   * @throws NullPointerException             If the provided email address is
   *                                          null.
   * @throws IllegalArgumentException         If the string of characters in
   *                                          the provided email address is
   *                                          empty or consists only of white
   *                                          characters.
   * @throws UserInvalidEmailAddressException If the email address provided is
   *                                          incorrect and does not meet the
   *                                          standards.
   */
  void checkEmail(final String email);

  /**
   * Checks whether the entered password is correct and meets password
   * standards.
   *
   * @throws NullPointerException         If the provided password is null.
   * @throws IllegalArgumentException     If the string of characters in the
   *                                      provided password is empty or
   *                                      consists only of white characters.
   * @throws UserInvalidPasswordException If the password provided is incorrect
   *                                      and does not meet the standards.
   */
  void checkPassword(final String password);
}
