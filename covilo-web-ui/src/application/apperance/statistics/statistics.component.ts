import { Component, OnInit } from "@angular/core"
import { timer } from "rxjs"
import { LocationCities, LocationCity, LocationCityService } from ".."

interface IndexedLocationCity extends LocationCity {
  index: number
}

@Component({
  selector: "a-statistics",
  templateUrl: "./statistics.component.html",
  styleUrls: [
    "./statistics.component.scss"
  ]
})
export class StatisticsComponent implements OnInit {

  // All available cities
  private cities?: LocationCities
  citiesInTable?: IndexedLocationCity[]
  size: number = 0
  page: number = 1
  pageItemsCount: number = 100

  constructor(private readonly locationCityService: LocationCityService) {
  }

  private refreshCities(): void {
    this.locationCityService.getAll().subscribe({
      next: (value: LocationCities) => {
        this.cities = value.sort((a: LocationCity, b: LocationCity) => {
          return a.domesticName.localeCompare(b.domesticName)
        })
        this.size = value.length
      }
    })
  }

  refreshCitiesInTable(): void {
    if (!this.cities) {
      throw Error("") // TODO error message
    }
    const count = this.pageItemsCount
    const product = (this.page - 1) * count
    this.citiesInTable = this.cities
    .map((city: LocationCity, i: number) => ({index: i + 1, ...city}))
    .slice(product, product + count)
  }

  ngOnInit(): void {
    this.refreshCities()
    timer(100).subscribe({
      next: () => this.refreshCitiesInTable()
    })
  }
}
