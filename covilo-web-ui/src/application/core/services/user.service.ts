import { Injectable } from "@angular/core"
import { ApiHttpClientService } from "../../shared/shared.module"

@Injectable({
  providedIn: "root"
})
export class UserService {

  constructor(private readonly api: ApiHttpClientService) {
  }
}
