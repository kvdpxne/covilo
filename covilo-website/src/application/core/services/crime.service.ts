import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ApiHttpClientService} from "../../shared";
import {Crime} from "../index";
import {Category} from "../models/category";
import {ReportCrimeRequest} from "../playloads/report-crime-request";
import {Book} from "../aggregation/book";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class CrimeService {

  private readonly api: ApiHttpClientService;

  public constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  private buildCategory(category: Category): Category {
    return new Category(
      category.identifier,
      category.name,
      category.classification
    );
  }

  public getCategories(): Observable<Category[]> {
    return this.api.get<Category[]>("search/categories").pipe(
      map(arr => arr.map(it => this.buildCategory(it)))
    );
  }

  public getCrimes(city: string): Observable<Book<Crime>> {
    return this.api.get<Book<Crime>>("crime/all", {
      city: city
    }).pipe(
      map(value => {
        value.content = value.content.map(it => new Crime(
          it.identifier,
          it.title,
          it.description,
          it.categories.map(category => this.buildCategory(category)),
          it.time,
          it.place,
          it.reporter,
          it.confirmed
        ));
        return value;
      })
    )
  }

  public report(request: ReportCrimeRequest): Observable<Crime> {
    return this.api.post<Crime>("crime/report", request);
  }
}
