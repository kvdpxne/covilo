import { Injectable } from "@angular/core"
import { Observable, tap } from "rxjs"
import { ApiHttpClientService } from "../shared"
import { TokenStorageService } from "./token-storage.service"
import { LoginCredentialsDto } from "./login-credentials-dto"
import { JwtResponse } from "./jwt-response"
import { Router } from "@angular/router"

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  constructor(
    private readonly router: Router,
    private readonly apiHttpClientService: ApiHttpClientService,
    private readonly tokenStorageService: TokenStorageService
  ) { }

  public login(credentials: LoginCredentialsDto): Observable<JwtResponse> {
    return this.apiHttpClientService
    .post<JwtResponse>("login", credentials)
    .pipe(
      tap((response: JwtResponse) => {
        this.tokenStorageService.token = response.token
      })
    )
  }

  public logout() {
    this.tokenStorageService.remove()
    this.router.navigateByUrl("/")
  }
}
