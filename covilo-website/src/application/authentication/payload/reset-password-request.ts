/**
 * Request sent in case of need to reset the user's password.
 */
export interface ResetPasswordRequest {

  /**
   * A unique email address to which the user account is assigned.
   */
  email: string;
}