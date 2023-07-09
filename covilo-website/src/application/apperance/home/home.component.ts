import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Cities, City, Countries, Country, Province, Provinces} from "src/application/core";
import {GeographicalService} from "../../core/services/geographical.service";
import {Continent} from "../../core/models/continent";
import {throwError} from "rxjs";

@Component({
  templateUrl: "./home.component.html",
  styleUrls: [
    "./home.component.scss"
  ]
})
export class HomeComponent implements OnInit {

  private readonly router: Router;

  private readonly geographicalService: GeographicalService;

  // available countries
  countries?: Countries;

  // available regions by country
  regions?: Provinces;

  // available cities by region
  cities?: Cities;

  // key of the selected country
  currentCountry?: Country;

  // key of the selected region
  currentProvince?: Province;

  // key of the selected city
  currentCity?: City;

  constructor(
    router: Router,
    geographicalService: GeographicalService
  ) {
    this.router = router;
    this.geographicalService = geographicalService;
  }

  /**
   * Overwrite the country key and fetch all regions for it
   */
  setCountry(country: Country): void {
    this.currentCountry = country;
    this.getAvailableRegions();
  }

  /**
   * Overwrite the region key and fetch all cities for it
   */
  setRegion(region: Province): void {
    this.currentProvince = region;
    this.getAvailableCities();
  }

  /**
   * Overwrite the city key
   */
  setCity(city: City): void {
    this.currentCity = city;
  }

  canEnabled(value?: any): boolean {
    return null == value;
  }

  ngOnInit(): void {
    // countries should always be available
    this.getAvailableCountries();
  }

  go(): void {
    const city: City | undefined = this.currentCity;
    if (!city) {
      return;
    }
    this.router.navigateByUrl(`result-details/${city.identifier}`)
      .catch(error => throwError(() => error));
  }

  /**
   * All available countries
   */
  private getAvailableCountries(): void {
    this.geographicalService.getCountriesByContinent(Continent.EUROPE).subscribe({
      next: (value: Countries): void => {
        this.countries = value.sort((a: Country, b: Country) => {
          return a.name.localeCompare(b.name);
        });
      }
    });
  }

  /**
   * All available regions for selected country
   */
  private getAvailableRegions(): void {
    if (!this.currentCountry) {
      throw new Error("Cannot get regions if country is undefined.");
    }
    this.geographicalService.getProvincesByCountry(this.currentCountry).subscribe({
      next: (value: Provinces): void => {
        this.regions = value.sort((a: Province, b: Province) => {
          return a.nationalName.localeCompare(b.nationalName);
        });
      }
    });
  }

  /**
   * All available cities for selected region
   */
  private getAvailableCities(): void {
    if (!this.currentProvince) {
      throw new Error("Cannot get cities if country or region is undefined.");
    }
    this.geographicalService.getCitiesByProvince(this.currentProvince).subscribe({
      next: (value: Cities): void => {
        this.cities = value.sort((a, b) => {
          return a.nationalName.localeCompare(b.nationalName);
        });
      }
    });
  }
}
