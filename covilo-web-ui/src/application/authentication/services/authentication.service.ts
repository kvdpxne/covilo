import { Injectable } from "@angular/core"
import { Router } from "@angular/router"
import { Observable } from "rxjs"

import { ApiHttpClientService } from "../../"

import { JwtResponse, LoginCredentials, StorageService } from "../"

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  private readonly baseUrl: string

  constructor(private readonly apiHttpClientService: ApiHttpClientService) {
    const apiUrl = apiHttpClientService.getUrl()
    this.baseUrl = apiUrl + "authentication"
  }

  signup(): Observable<JwtResponse> {
    const url = `${this.baseUrl}/signup`
    return this.apiHttpClientService.post<JwtResponse>(url, {})
  }

  login(credentials: LoginCredentials): Observable<JwtResponse> {
    const url = "authentication/login"
    return this.apiHttpClientService.post<JwtResponse>(url, credentials)
  }

  /**
   * Log out user
   */
  logout(email: string): Observable<void> {
    const url = "authentication/logout"
    return this.apiHttpClientService.post<void>(url, email)
  }

  refresh() {
    return this.apiHttpClientService.post("refresh", {})
  }
}
