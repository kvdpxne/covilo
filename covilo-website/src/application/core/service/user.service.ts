import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {ChangePasswordRequest} from "../playload/change-password-request";

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
    return this.httpClientService.get<User>("me");
  }

  public updatePassword(
    request: ChangePasswordRequest
  ): Observable<void> {
    return this.httpClientService.put<void>("me/password", request)
  }

  public uploadAvatar(file: FormData): Observable<void> {
    return this.httpClientService.post<void>("me/avatar", file);
  }

  public deleteAvatar(): Observable<void> {
    return this.httpClientService.delete<void>("me/delete");
  }
}
