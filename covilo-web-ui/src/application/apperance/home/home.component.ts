import { Component, OnInit } from "@angular/core"
import { Router } from "@angular/router"
import {
  LocationCity,
  LocationCityService,
  LocationCountry,
  LocationCountryService,
  LocationRegion,
  LocationRegionService
} from "../../core/core.module"

@Component({
  templateUrl: "./home.component.html",
  styleUrls: [
    "./home.component.scss"
  ]
})
export class HomeComponent implements OnInit {

  // available countries
  countries?: Array<LocationCountry>

  // available regions by country
  regions?: Array<LocationRegion>

  // available cities by region
  cities?: Array<LocationCity>

  // key of the selected country
  countryKey?: string

  // key of the selected region
  regionKey?: string

  // key of the selected city
  cityKey?: string

  // TODO move
  items: string[] = [
    "collection",
    "person-circle",
    "toggles2"
  ]

  constructor(
    private router: Router,
    private countryService: LocationCountryService,
    private regionService: LocationRegionService,
    private cityService: LocationCityService
  ) {
  }

  /**
   * Overwrite the country key and fetch all regions for it
   */
  setCountry(country: LocationCountry): void {
    this.countryKey = country.key
    this.getAvailableRegions()
  }

  /**
   * Overwrite the region key and fetch all cities for it
   */
  setRegion(region: LocationRegion): void {
    this.regionKey = region.key
    this.getAvailableCities()
  }

  /**
   * Overwrite the city key
   */
  setCity(city: LocationCity): void {
    this.cityKey = city.key
  }

  canEnabled(value?: string): boolean {
    return null == value
  }

  ngOnInit(): void {
    // countries should always be available
    this.getAvailableCountries()
  }

  go() {
    const country = this.countryKey
    const region = this.regionKey
    const city = this.cityKey
    const path = `result-details/${country}/${region}/${city}`
    this.router.navigateByUrl(path).then(it => {
      // TODO ???
    })
  }

  /**
   * All available countries
   */
  private getAvailableCountries(): void {
    this.countryService.getAll().subscribe({
      next: value => {
        this.countries = value.sort((a, b) => {
          return a.key.localeCompare(b.key)
        })
      }
    })
  }

  /**
   * All available regions for selected country
   */
  private getAvailableRegions(): void {
    if (!this.countryKey) {
      throw new Error("Cannot get regions if country is undefined.")
    }
    this.regionService.getAll(this.countryKey).subscribe({
      next: value => {
        this.regions = value.sort((a, b) => {
          return a.domesticName.localeCompare(b.domesticName)
        })
      }
    })
  }

  /**
   * All available cities for selected region
   */
  private getAvailableCities(): void {
    if (!this.regionKey && !this.countryKey) {
      throw new Error("Cannot get cities if country or region is undefined.")
    }
    this.cityService.getAll(this.countryKey, this.regionKey).subscribe({
      next: value => {
        this.cities = value.sort((a, b) => {
          return a.domesticName.localeCompare(b.domesticName)
        })
      }
    })
  }
}
