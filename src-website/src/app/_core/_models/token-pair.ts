import {TokenType} from "./token-type";

/**
 *
 */
export interface TokenPair {

  /**
   *
   */
  accessToken: string;

  /**
   *
   */
  refreshToken: string;

  expiry: Date;

  type: TokenType;
}