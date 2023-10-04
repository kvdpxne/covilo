import { Injectable } from "@angular/core"
import { ApiHttpClientService } from "../../shared"
import {Observable} from "rxjs";
import {User} from "../model/user";

@Injectable({
  providedIn: "root"
})
export class UserService {

  private readonly api: ApiHttpClientService;

  constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getMe(): Observable<User> {
    return this.api.get2<User>("me", {})
  }

  public viewAvatar(): Observable<string> {
    return this.api.get2<string>("me/avatar")
  }
}
