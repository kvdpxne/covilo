import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Continent} from "../models/continent";
import {Observable} from "rxjs";
import {Country} from "../models/country";
import {Province} from "../models/province";
import {City} from "../models/city";

@Injectable({
  providedIn: "root"
})
export class GeographicalService {

  private readonly api: ApiHttpClientService;

  constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCountriesByContinent(continent: Continent): Observable<Country[]> {
    return this.api.get2("geographical/countries", {});
  }

  public getProvincesByCountry(country: Country): Observable<Province[]> {
    return this.api.get2("geographical/provinces", {
      country: country.identifier
    });
  }

  public getCitiesByProvince(province: Province): Observable<City[]> {
    return this.api.get2("geographical/cities", {
      province: province.identifier
    });
  }

  public getCity(identifier: string): Observable<City> {
    return this.api.get2<City>("geographical/city", {
      identifier: identifier
    });
  }
}
