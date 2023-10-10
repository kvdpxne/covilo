import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
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
    return this.api.get2<User>("me", {}).pipe();
  }

  public uploadAvatar(file: FormData): Observable<void> {
    return this.api.post2<void>("me/avatar", file);
  }

  public deleteAvatar(): Observable<void> {
    return this.api.delete<void>("me/delete");
  }
}
