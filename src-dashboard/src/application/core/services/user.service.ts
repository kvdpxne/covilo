import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {UserMeChangePasswordRequest} from "../playloads/user-me-change-password-request";
import {UserMeChangeEmailRequest} from "../playloads/user-me-change-email-request";

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

  public updateEmail(
    request: UserMeChangeEmailRequest
  ): Observable<void> {
    return this.httpClientService.put<void>("me/email", request);
  }

  public updatePassword(
    request: UserMeChangePasswordRequest
  ): Observable<void> {
    return this.httpClientService.put<void>("me/password", request);
  }

  public uploadAvatar(file: FormData): Observable<void> {
    return this.httpClientService.post<void>("me/avatar", file);
  }

  public deleteAvatar(): Observable<void> {
    return this.httpClientService.delete<void>("me/delete");
  }
}
