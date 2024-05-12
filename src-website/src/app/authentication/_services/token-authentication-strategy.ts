import {AuthenticationStrategy} from "./authentication-strategy";
import {TokenPair} from "../../_core/_models/token-pair";
import {inMemoryStorage} from "../../_shared/_services/in-memory-storage";
import {StorageKey} from "../../_shared/_services/storage-key";

class TokenAuthenticationStrategy
  implements AuthenticationStrategy<TokenPair> {

  public getTokePair(): TokenPair {
    return inMemoryStorage.get<TokenPair>(StorageKey.USER_TOKEN)
  }

  public isLogged(): boolean {
    return inMemoryStorage.has(StorageKey.USER_TOKEN);
  }

  public doLogin(data: TokenPair): void {
    inMemoryStorage.store<TokenPair>(StorageKey.USER_TOKEN, data);
  }

  public doLogout(): void {
    inMemoryStorage.remove(StorageKey.USER_TOKEN);
  }
}

export const tokenAuthenticationStrategy = new TokenAuthenticationStrategy();