import { Injectable } from "@angular/core"
import { User } from "../../core"

//
const USER_KEY = "authenticated-user"

@Injectable({
  providedIn: "root"
})
export class StorageService {

  getUser(): User | null {
    const content = sessionStorage.getItem(USER_KEY)
    if (null === content) {
      return null
    }
    return JSON.parse(content)
  }

  setUser(user: User): void {
    sessionStorage.removeItem(USER_KEY)
    sessionStorage.setItem(USER_KEY, JSON.stringify(user))
  }

  isLogged(): boolean {
    return null !== sessionStorage.getItem(USER_KEY)
  }

  clean(): void {
    sessionStorage.removeItem(USER_KEY)
  }
}
