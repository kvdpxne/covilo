import {Injectable} from "@angular/core";
import {HttpBridge} from "../../shared/services/http/http-bridge";
import {ApiHttpBridge} from "../../shared/services/http/api-http-bridge";
import {Observable} from "rxjs";
import {Page} from "../aggregation/page";
import {PageRequest} from "../aggregation/page-request";
import {HttpParams} from "@angular/common/http";

export type RESPONSE<T> = Observable<Page<T> | Array<T>>

@Injectable({
  providedIn: "root"
})
export class GeolocationService {

  private readonly httpBridge: HttpBridge;

  public constructor(
    apiHttpBridge: ApiHttpBridge
  ) {
    this.httpBridge = apiHttpBridge;
  }

  public getCities(
    pageRequest?: PageRequest
  ): Observable<any> {
    return this.httpBridge.get(
      "api/v1/geolocation/city",
      options => {
        const parameters = new HttpParams();

        if (pageRequest) {
          const {pageSize, pageNumber} = pageRequest;
          parameters.set("page", pageSize).set("size", pageNumber);
        }

        options.params = parameters;

        return options;
      }
    )
  }
}
