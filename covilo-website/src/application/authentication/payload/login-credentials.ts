export interface LoginCredentials {

  /**
   * A unique email address to which the user account is assigned or a unique,
   * friendly username, if it has been set.
   */
  recognizableName: string;

  /**
   * Password for accessing the user account.
   */
  password: string;
}