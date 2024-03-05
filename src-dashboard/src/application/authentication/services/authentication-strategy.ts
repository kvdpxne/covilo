/**
 * Interface defining the contract for an authentication strategy.
 *
 * This interface provides methods to check if a user is logged in, perform
 * a login operation, and perform a logout operation.
 *
 * @param T The type of data required for the login operation.
 */
export interface AuthenticationStrategy<T> {

  /**
   * Checks if a user is currently logged in.
   *
   * @return True if the user is logged in, false otherwise.
   */
  isLogged(): boolean;

  /**
   * Performs a login operation using the provided data.
   *
   * @param data The data required for the login operation.
   */
  doLogin(data: T): void;

  /**
   * Performs a logout operation.
   */
  doLogout(): void;
}