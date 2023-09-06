import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ApiHttpClientService} from "../../shared";
import {Crime} from "../index";
import {Category} from "../models/category";
import {ReportCrimeRequest} from "../playload/report-crime-request";

@Injectable({
  providedIn: "root"
})
export class CrimeService {

  private readonly api: ApiHttpClientService;

  public constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCategories(): Observable<Category[]> {
    return this.api.get2("search/categories")
  }

  public getCrimes(city: string): Observable<Crime[]> {
    return this.api.get2<Crime[]>("crimes", {
      city: city
    });
  }

  public report(request: ReportCrimeRequest): Observable<Crime> {
    return this.api.post2<Crime>("crime/report", request)
  }
}
