import { Component, OnInit } from "@angular/core"
import { LocationCity, LocationCityService } from "../../../core"

@Component({
  selector: "a-statistics",
  templateUrl: "./statistics.component.html",
  styleUrls: [
    "./statistics.component.scss"
  ]
})
export class StatisticsComponent implements OnInit {

  // All available cities
  cities?: Array<LocationCity>

  constructor(private cityService: LocationCityService) {
  }

  /**
   * Gets all the available cities
   */
  getCities(): void {
    this.cityService.getAll().subscribe({
      next: value => this.cities = value.sort((a, b) => {
        return a.domesticName.localeCompare(b.domesticName)
      })
    })
  }

  ngOnInit(): void {
    this.getCities()
  }
}
