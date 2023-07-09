import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ApiHttpClientService} from "../../shared";
import {Crimes} from "../index";

@Injectable({
  providedIn: "root"
})
export class CrimeService {

  private readonly api: ApiHttpClientService;

  public constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCrimes(city: string): Observable<Crimes> {
    return this.api.get2<Crimes>("crimes", {
      city: city
    });
  }
}
