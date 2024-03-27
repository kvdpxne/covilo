import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {HttpBridge} from "../../shared/services/http/http-bridge";
import {ApiHttpBridge} from "../../shared/services/http/api-http-bridge";
import {HttpParams} from "@angular/common/http";
import {Page} from "../aggregation/page";
import {PageRequest} from "../aggregation/page-request";
import {
  HttpRequestOptions
} from "../../shared/services/http/http-request-options";

@Injectable({
  providedIn: "root"
})
export class UserService {

  private readonly httpBridge: HttpBridge;

  public constructor(
    apiHttpBridge: ApiHttpBridge
  ) {
    this.httpBridge = apiHttpBridge;
  }

  /**
   * Retrieves a page of users from the backend.
   *
   * If no page request is provided, the first page with a default page size
   * of 20 is requested.
   *
   * @param pageRequest The page request is specifying the page number and
   * page size.
   * @returns An observable emitting the requested page of users.
   */
  public getUsers(
    pageRequest: PageRequest = {
      pageNumber: 0,
      pageSize: 20
    }
  ): Observable<Page<User>> {
    return this.httpBridge.get<Page<User>>(
      "api/v1/user/all",
      (options: HttpRequestOptions): HttpRequestOptions => {
        // Configure parameters for pagination
        options.params = new HttpParams()
          .append("page", pageRequest.pageNumber)
          .append("size", pageRequest.pageSize);

        return options;
      }
    );
  }
}
