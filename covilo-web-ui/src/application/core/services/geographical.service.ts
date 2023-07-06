import {Injectable} from '@angular/core';
import {ApiHttpClientService} from "../../shared";
import {Continent} from "../models/continent";
import {Observable} from "rxjs";
import {Countries, Country} from "../models/country";
import {Province, Provinces} from "../models/province";
import {Cities} from "../models/city";

@Injectable({
  providedIn: 'root'
})
export class GeographicalService {

  private readonly api: ApiHttpClientService;

  constructor(api: ApiHttpClientService) {
    this.api = api;
  }

  public getCountriesByContinent(continent: Continent): Observable<Countries> {
    return this.api.get2<Countries>("geographical/countries", {
      continent: continent
    });
  }

  public getProvincesByCountry(country: Country): Observable<Provinces> {
    return this.api.get2<Provinces>("geographical/provinces", {
      country: country
    });
  }

  public getCitiesByProvince(province: Province): Observable<Cities> {
    return this.api.get2<Cities>("geographical/cities", {
      province: province
    });
  }
}
