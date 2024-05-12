export interface TokenPair {

  /**
   * An access token to the restricted API controller endpoints, assigned to a
   * user for a specified period of time.
   */
  accessToken: string;

  refreshToken: string;
}