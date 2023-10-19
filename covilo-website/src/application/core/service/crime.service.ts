import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ApiHttpClientService} from "../../shared";
import {Crime} from "../index";
import {Category} from "../model/category";
import {ReportCrimeRequest} from "../playload/report-crime-request";
import {Book} from "../aggregation/book";

@Injectable({
  providedIn: "root"
})
export class CrimeService {

  private readonly api: ApiHttpClientService;

  public constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCategories(): Observable<Category[]> {
    return this.api.get("search/categories");
  }

  public getCrimes(city: string): Observable<Book<Crime>> {
    return this.api.get("crimes", {
      city: city
    });
  }

  public report(request: ReportCrimeRequest): Observable<Crime> {
    return this.api.post<Crime>("crime/report", request);
  }
}
