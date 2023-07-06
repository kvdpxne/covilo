import {AfterViewInit, Component, OnDestroy, OnInit} from "@angular/core"
import {ActivatedRoute} from "@angular/router"
import {
  Crime,
  CrimeService,
  City,
  LocationCityService
} from "src/application/core"
import {Subscription} from "rxjs"

@Component({
  selector: "a-result-details",
  templateUrl: "./result-details.component.html"
})
export class ResultDetailsComponent implements AfterViewInit, OnInit, OnDestroy {

  //
  private crimeSubscription?: Subscription

  //
  city!: City

  // All available crimes for the city
  crimes?: Array<Crime>

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
      next: (value: City) => {
        this.city = value
      }
    })
  }

  ngOnDestroy(): void {
    this.crimeSubscription?.unsubscribe()
  }
}
