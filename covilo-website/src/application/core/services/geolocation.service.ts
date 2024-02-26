import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Continent} from "../models/continent";
import {Observable} from "rxjs";
import {Country} from "../models/country";
import {Province} from "../models/province";
import {City} from "../models/city";
import {Book} from "../aggregation/book";

const GEOLOCATION_PORT = "geolocation"

@Injectable({
  providedIn: "root"
})
export class GeolocationService {

  private readonly api: ApiHttpClientService;

  constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCountries(): Observable<Book<Country>> {
    return this.api.get(`${GEOLOCATION_PORT}/countries`);
  }

  public getProvincesByCountry(country: Country): Observable<Book<Province>> {
    return this.api.get(`${GEOLOCATION_PORT}/regions`, {
      country: country.identifier
    });
  }

  public getCitiesByProvince(province: Province): Observable<Book<City>> {
    return this.api.get(`${GEOLOCATION_PORT}/cities`, {
      provinceIdentifier: province.identifier
    });
  }

  public getCity(identifier: string): Observable<City> {
    return this.api.get<City>(`${GEOLOCATION_PORT}/city`, {
      identifier: identifier
    });
  }
}
