import { AfterViewInit, Component, OnDestroy, OnInit } from "@angular/core"
import { ActivatedRoute } from "@angular/router"
import {
  Crime,
  CrimeService,
  LocationCity,
  LocationCityService
} from "../../core/core.module"
import { ChartConfiguration, ChartData, ChartType } from "chart.js"
import { Subscription } from "rxjs"

@Component({
  selector: "a-result-details",
  templateUrl: "./result-details.component.html"
})
export class ResultDetailsComponent implements AfterViewInit, OnInit,
  OnDestroy {

  //
  private crimeSubscription?: Subscription

  //
  city!: LocationCity

  // All available crimes for the city
  crimes?: Array<Crime>

  public barChartOptions: ChartConfiguration["options"] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      x: {},
      y: {
        min: 10
      }
    },
    plugins: {
      legend: {
        display: true
      }
    }
  }
  public barChartType: ChartType = "bar"
  public barChartPlugins = []
  public barChartData: ChartData<"bar"> = {
    labels: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
    datasets: [
      {data: [97, 31, 19, 60, 85, 57, 27], label: "Series A"},
      {data: [75, 76, 49, 69, 84, 33, 60], label: "Series B"},
      {data: [91, 50, 15, 67, 22, 98, 60], label: "Series C"},
      {data: [20, 62, 78, 96, 21, 88, 65], label: "Series D"}
    ]
  }

  constructor(private readonly route: ActivatedRoute,
              private readonly locationCityService: LocationCityService,
              private readonly crimeService: CrimeService
  ) {
  }

  private getCity(): void {

  }

  private getCrimes(
    country: string,
    region: string,
    city: string
  ): void {
    this.crimeSubscription = this.crimeService.getAll(country, region, city).subscribe({
      next: (value: Array<Crime>) => {
        this.crimes = value
      }
    })
    if (!this.city) {

    }
  }

  ngAfterViewInit(): void {

  }

  ngOnInit(): void {
    let country = ""
    let region = ""
    let city = ""
    this.route.params.subscribe(parameter => {
      country = parameter["country"]
      region = parameter["region"]
      city = parameter["city"]
    })

    //
    this.getCrimes(country, region, city)

    this.locationCityService.getOne(country, region, city).subscribe({
      next: (value: LocationCity) => {
        this.city = value
      }
    })
  }

  ngOnDestroy(): void {
    this.crimeSubscription?.unsubscribe()
  }
}
