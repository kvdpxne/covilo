import { Injectable } from "@angular/core"

const TOKEN_KEY = "access_token"

@Injectable({
  providedIn: "root"
})
export class TokenStorageService {

  get token(): string {
    // @ts-ignore
    return sessionStorage.getItem(TOKEN_KEY)
  }

  set token(newToken: string) {
    sessionStorage.setItem(TOKEN_KEY, newToken)
  }

  remove() {
    sessionStorage.removeItem(TOKEN_KEY)
  }
}
