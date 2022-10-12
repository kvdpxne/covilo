import { Component, OnInit } from "@angular/core"
import { ActivatedRoute } from "@angular/router"
import { LocationCountryService, LocationRegionService } from ".."

@Component({
  selector: "a-result-list",
  templateUrl: "./result-list.component.html",
  styleUrls: [
    "./result-list.component.scss"
  ]
})
export class ResultListComponent implements OnInit {

  //
  private countryKey?: string

  //
  private regionKey?: string

  constructor(
    private readonly router: ActivatedRoute,
    private readonly countryService: LocationCountryService,
    private readonly regionService: LocationRegionService
  ) {
    this.router.params.subscribe(param => {
      this.countryKey = param["country"]
      this.regionKey = param["region"]
    })
  }

  ngOnInit(): void {
  }

}
