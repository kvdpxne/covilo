/**
 *
 */
export enum StorageKey {

  /**
   * Key for storing the sensitive user token.
   * This token is associated with the currently logged-in user.
   *
   * Caution: Handle this data with care to prevent unauthorized access.
   */
  USER_TOKEN = "user_token",

  /**
   * Key for storing the user authentication status.
   * This key is used to determine whether the user is currently authenticated.
   */
  USER_IS_AUTHENTICATED = "user_is_authenticated",

  /**
   * Key for storing the sensitive authenticated user data.
   * This data contains information about the authenticated user.
   *
   * Caution: Protect this data from unauthorized access to maintain user
   * privacy.
   */
  AUTHENTICATED_USER = "authenticated_user",
}