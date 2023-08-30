import { Component } from '@angular/core';
import {City, Country, CrimeService, GeographicalService, Province} from "../core";
import {ActivatedRoute, UrlSegment} from "@angular/router";

@Component({
  selector: "reporting-route",
  templateUrl: './reporting.component.html',
  styleUrls: ['./reporting.component.scss']
})
export class ReportingComponent {

  private readonly geographicalService: GeographicalService;

  private readonly crimeService: CrimeService;

  /**
   *
   */
  public city?: City

  constructor(
    route: ActivatedRoute,
    geographicalService: GeographicalService,
    crimeService: CrimeService
  ) {
    this.geographicalService = geographicalService;
    this.crimeService = crimeService;

    const segments: UrlSegment[] = route.snapshot.url;
    if (0 < segments.length) {
      const last: string = segments[segments.length - 1].path
      this.geographicalService.getCity(last).subscribe({
        next: (city: City): void => {
          this.city = city;
        }
      });
    }
  }

  public get province(): Province {
    return this.city?.province!!;
  }

  public get country(): Country {
    return this.province.country!!;
  }

  /**
   *
   */
  public submit(): void {

  }
}
