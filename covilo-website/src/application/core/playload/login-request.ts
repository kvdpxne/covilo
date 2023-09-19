export interface LoginRequest {

  /**
   * A unique email address to which the user account is assigned or a unique,
   * friendly username, if it has been set.
   */
  email: string;

  /**
   * Password for accessing the user account.
   */
  password: string;
}