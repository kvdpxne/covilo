import { Component, OnInit } from "@angular/core"
import { timer } from "rxjs"
import { Cities, City, LocationCityService } from "../../core"

interface IndexedLocationCity extends City {
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
  private cities?: Cities
  citiesInTable?: IndexedLocationCity[]
  size: number = 0
  page: number = 1
  pageItemsCount: number = 100

  constructor(private readonly locationCityService: LocationCityService) {
  }

  private refreshCities(): void {
    this.locationCityService.getAll().subscribe({
      next: (value: Cities) => {
        this.cities = value.sort((a: City, b: City) => {
          return a.nationalName.localeCompare(b.nationalName)
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
    .map((city: City, i: number) => ({index: i + 1, ...city}))
    .slice(product, product + count)
  }

  ngOnInit(): void {
    this.refreshCities()
    timer(100).subscribe({
      next: () => this.refreshCitiesInTable()
    })
  }
}
