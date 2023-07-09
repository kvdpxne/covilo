import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Continent} from "../models/continent";
import {Observable} from "rxjs";
import {Countries, Country} from "../models/country";
import {Province, Provinces} from "../models/province";
import {Cities, City} from "../models/city";

@Injectable({
  providedIn: "root"
})
export class GeographicalService {

  private readonly api: ApiHttpClientService;

  constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCountriesByContinent(continent: Continent): Observable<Countries> {
    return this.api.get2<Countries>("geographical/countries", {});
  }

  public getProvincesByCountry(country: Country): Observable<Provinces> {
    return this.api.get2<Provinces>("geographical/provinces", {
      country: country.identifier
    });
  }

  public getCitiesByProvince(province: Province): Observable<Cities> {
    return this.api.get2<Cities>("geographical/cities", {
      province: province.identifier
    });
  }

  public getCity(identifier: string): Observable<City> {
    return this.api.get2<City>("geographical/city", {
      identifier: identifier
    });
  }
}
