import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ApiHttpClientService} from "../../shared";
import {Crimes} from "../index";
import {Category} from "../models/category";

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

  public getCrimes(city: string): Observable<Crimes> {
    return this.api.get2<Crimes>("crimes", {
      city: city
    });
  }
}
