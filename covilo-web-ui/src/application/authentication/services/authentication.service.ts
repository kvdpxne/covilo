import { Injectable } from "@angular/core"
import { ApiHttpClientService } from "../../shared/shared.module"

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  constructor(private readonly api: ApiHttpClientService) {
  }

  public logout(): void {

  }
}
