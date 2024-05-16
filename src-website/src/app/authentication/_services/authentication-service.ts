import {post} from "../../_shared/_services/http-bridge";
import {TokenPair} from "../../_core/_models/token-pair";
import {me} from "../../_core/_services/me-service";
import {User} from "../../_core/_models/user";
import {tokenAuthenticationStrategy} from "./token-authentication-strategy";
import {inMemoryStorage} from "../../_shared/_services/in-memory-storage";
import {logger} from "../../_shared/_services/logger";

interface LoginRequest {
  email: string;
  password: string;
}

/**
 *
 */
const endpoint: string = "api/v1/authentication"

const cacheMe = async () => {
  const currentUser: User = await me();
  console.log(currentUser);
  inMemoryStorage.store("USER", currentUser);
};

/**
 *
 */
const login = async (
  request: LoginRequest
): Promise<void> => {
  const tokenPair = await post<TokenPair>(`${endpoint}/login`, request);
  tokenAuthenticationStrategy.doLogin(tokenPair);
  await cacheMe();
  logger.debug(() => "The user has been successfully logged in.")
};

interface SignupRequest {
  firstName: string;
  lastName: string;
  birthDate: Date;
  email: string;
  password: string;
  confirmPassword: string;
  privacy: boolean;
}

const signup = async (
  request: SignupRequest
): Promise<void> => {
  await post<SignupRequest>(`${endpoint}/signup`, request);

}

const refreshToken = async (tokenPair: TokenPair): Promise<void> => {

}

export {
  login
};