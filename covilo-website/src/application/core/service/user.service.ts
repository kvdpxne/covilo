import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Observable} from "rxjs";
import {User} from "../model/user";

@Injectable({
  providedIn: "root"
})
export class UserService {

  private readonly httpClientService: ApiHttpClientService;

  public constructor(httpClientService: ApiHttpClientService) {
    this.httpClientService = httpClientService;
  }

  /**
   *
   */
  public get me(): Observable<User> {
    return this.httpClientService.get2<User>("me");
  }

  public uploadAvatar(file: FormData): Observable<void> {
    return this.httpClientService.post2<void>("me/avatar", file);
  }

  public deleteAvatar(): Observable<void> {
    return this.httpClientService.delete<void>("me/delete");
  }
}
