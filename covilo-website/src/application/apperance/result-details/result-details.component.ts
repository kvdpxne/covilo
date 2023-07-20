import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {
  CrimeService,
  City,
  Crimes, Crime
} from "src/application/core";
import {GeographicalService} from "../../core";
import {Category} from "../../core/models/category";

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

  public categories?: Category[];
  public filterCrimes?: Crime[];

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
      this.crimeService.getCrimes(lastSegment).subscribe(it => {
        this.crimes = it
        // this.filterCrimes = it
      });
    }

    this.crimeService.getCategories().subscribe(it => this.categories = it)
  }

  public getCategories(crime: Crime): string {
    if (crime.title) {
      return crime.title;
    }
    if (!crime.categories || 0 >= crime.categories.length) {
      return crime.identifier;
    }
    let categories: string = "";
    for (let i: number = 0; i < crime.categories.length; i++) {
      categories += crime.categories[i].name;
      if (i < (crime.categories.length - 1)) {
        categories += ", ";
      }
    }
    return categories;
  }

  filterBy(category: Category) {
    this.filterCrimes = [];
    if (!this.crimes) {
      return;
    }
    for (const crime of this.crimes) {
      const categories = crime.categories;
      if (!categories || 0 === categories.length) {
        continue;
      }
      for (const category2 of categories) {
        if (category2.identifier === category.identifier) {
          this.filterCrimes.push(crime);
          // console.log(category2.name)
        }
      }
    }
    // console.log(this.filterCrimes)
  }
}
