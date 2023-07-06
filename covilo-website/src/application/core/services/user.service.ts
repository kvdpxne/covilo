import { Injectable } from "@angular/core"
import { ApiHttpClientService } from "../../shared"

@Injectable({
  providedIn: "root"
})
export class UserService {

  constructor(private readonly api: ApiHttpClientService) {
  }
}
