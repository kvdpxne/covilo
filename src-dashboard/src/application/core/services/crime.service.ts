import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ApiHttpClientService} from "../../shared";
import {Crime} from "../index";
import {Category} from "../models/category";
import {ReportCrimeRequest} from "../playloads/report-crime-request";
import {Book} from "../aggregation/book";
import {map} from "rxjs/operators";
import {HttpBridge} from "../../shared/services/http/http-bridge";
import {ApiHttpBridge} from "../../shared/services/http/api-http-bridge";

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

  public get crimesCount(): Observable<number> {
    return this.httpBridge.get<number>("api/v1/crime/count");
  }
}
