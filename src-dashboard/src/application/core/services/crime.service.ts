import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpBridge} from "../../shared/services/http/http-bridge";
import {ApiHttpBridge} from "../../shared/services/http/api-http-bridge";
import {Crime} from "../models/crime";
import {Page} from "../aggregation/page";
import {PageRequest} from "../aggregation/page-request";
import {SortingRequest} from "../aggregation/sorting-request";
import {
  HttpRequestOptions
} from "../../shared/services/http/http-request-options";
import {HttpParams} from "@angular/common/http";

export interface CrimeSearchCriteria {

  wasBefore: Date;

  wasAfter: Date;
}

@Injectable({
  providedIn: "root"
})
export class CrimeService {

  private readonly httpBridge: HttpBridge;

  public constructor(
    apiHttpBridge: ApiHttpBridge
  ) {
    this.httpBridge = apiHttpBridge;
  }

  /**
   *
   */
  public requestCrimes(
    searchCriteria?: CrimeSearchCriteria,
    pageRequest?: PageRequest,
    sortingRequest?: SortingRequest
  ): Observable<Page<Crime> | Crime[]> {
    //
    const path: string = "api/v1/crimes";

    //
    if (!searchCriteria && !pageRequest && !sortingRequest) {
      return this.httpBridge.get<Crime[]>(path);
    }

    return this.httpBridge.get<Page<Crime> | Crime[]>(
      path,
      (options: HttpRequestOptions): HttpRequestOptions => {
        //
        const parameters: HttpParams = new HttpParams();

        //
        if (pageRequest) {
          const {pageNumber, pageSize} = pageRequest;
          parameters.set("page", pageSize).set("size", pageNumber);
        }

        //
        options.params = parameters;

        //
        return options;
      }
    );

    return this.httpBridge.get("api/v1/crimes", options => {


      return options;
    });
  }

  public get crimesCount(): Observable<number> {
    return this.httpBridge.get<number>("api/v1/crime/count");
  }
}
