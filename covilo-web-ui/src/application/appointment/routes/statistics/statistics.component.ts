import { Component, OnInit } from '@angular/core';
import { LocationCity, LocationCityService } from "../../../core/core.module";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
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
      next: value => this.cities = value,
      complete: () => {
        console.debug(this.cities)
        this.cities?.sort((a, b) => {
          const aCity = a.key
          const bCity = b.key
          return aCity > bCity ? 1 : -1
        })
      }
    })
  }

  ngOnInit(): void {
    this.getCities()
  }
}
