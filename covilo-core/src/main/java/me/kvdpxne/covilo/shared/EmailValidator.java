package me.kvdpxne.covilo.shared;

public final class EmailValidator {

  public static void checkEmail(final String email) {
    if (null == email || email.isBlank()) {
      throw new NullPointerException("");
    }

    /* Gmail Special Case for Emails
     *
     * There's one special case that applies only to the Gmail domain: it's
     * permission to use the character + character in the local part of the
     * email. For the Gmail domain, the two email addresses
     * username+something@gmail.com and username@gmail.com are the same.
     *
     * Also, username@gmail.com is similar to user+name@gmail.com.
     *
     * We must implement a slightly different regex that will pass the email
     * validation for this special case as well:
     */
    var pattern = "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[^-]"
      + "[A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$";

    if (!email.matches(pattern)) {
      throw new EmailValidationFailedException("", email);
    }
  }
}
