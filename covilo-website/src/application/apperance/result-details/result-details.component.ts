import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {
  CrimeService,
  City,
  Crimes
} from "src/application/core";
import {GeographicalService} from "../../core/services/geographical.service";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "a-result-details",
  templateUrl: "./result-details.component.html"
})
export class ResultDetailsComponent implements OnInit {

  private readonly route: ActivatedRoute;

  private readonly geographicalService: GeographicalService;
  private readonly crimeService: CrimeService;

  public city?: City;
  public crimes?: Crimes;

  public page = 1;

  public constructor(
    route: ActivatedRoute,
    geographicalService: GeographicalService,
    crimeService: CrimeService
  ) {
    this.route = route;
    this.geographicalService = geographicalService;
    this.crimeService = crimeService;
  }

  public ngOnInit(): void {
    const url: UrlSegment[] = this.route.snapshot.url;

    if (0 < url.length) {
      const lastSegment: string = url[url.length - 1].path;

      this.geographicalService.getCity(lastSegment).subscribe(it => this.city = it);
      this.crimeService.getCrimes(lastSegment).subscribe(it => this.crimes = it);
    }
  }
}
