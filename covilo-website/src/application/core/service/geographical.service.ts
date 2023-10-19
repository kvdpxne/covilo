import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Continent} from "../model/continent";
import {Observable} from "rxjs";
import {Country} from "../model/country";
import {Province} from "../model/province";
import {City} from "../model/city";
import {Book} from "../aggregation/book";

@Injectable({
  providedIn: "root"
})
export class GeographicalService {

  private readonly api: ApiHttpClientService;

  constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCountries(): Observable<Book<Country>> {
    return this.api.get("geographical/countries");
  }

  public getCountriesByContinent(continent: Continent): Observable<Country[]> {
    return this.api.get("geographical/countries", {});
  }

  public getProvincesByCountry(country: Country): Observable<Book<Province>> {
    return this.api.get("geographical/provinces", {
      country: country.identifier
    });
  }

  public getCitiesByProvince(province: Province): Observable<Book<City>> {
    return this.api.get("geographical/cities", {
      provinceIdentifier: province.identifier
    });
  }

  public getCity(identifier: string): Observable<City> {
    return this.api.get<City>("geographical/city", {
      identifier: identifier
    });
  }
}
