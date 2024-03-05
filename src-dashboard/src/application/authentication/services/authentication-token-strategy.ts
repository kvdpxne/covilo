import {Injectable} from "@angular/core";
import {AuthenticationStrategy} from "./authentication-strategy";
import {BrowserStorageService} from "../../shared";
import {Token} from "../../core";

@Injectable({
  providedIn: "root"
})
export class AuthenticationTokenStrategy
  implements AuthenticationStrategy<Token> {

  public constructor(
    private readonly browserStorageService: BrowserStorageService
  ) {

  }

  public getToken(): Token | null {
    return this.browserStorageService.loadFromLocalStorage<Token>("token");
  }

  public isLogged(): boolean {
    const key = "token";
    if (this.browserStorageService.hasInLocalStorage(key)) {
      return true;
    }

    this.browserStorageService.deleteFromLocalStorage(key);
    return false;
  }

  public doLogin(data: Token): void {
    this.browserStorageService.storeInLocalStorage("token", data);
  }

  public doLogout(): void {
    this.browserStorageService.deleteFromLocalStorage("token");
  }
}
