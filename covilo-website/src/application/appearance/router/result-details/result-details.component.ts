import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {City, Crime, CrimeService} from "src/application/core";
import {Category, GeographicalService} from "../../../core";

@Component({
  selector: "router-result-details",
  templateUrl: "./result-details.component.html"
})
export class ResultDetailsComponent implements OnInit {

  /**
   * Maximum number of elements that can be displayed on a single page.
   */
  public readonly PAGE_ITEM_COUNT: number;

  /**
   * Maximum number of pages that can be displayed.
   */
  public readonly PAGE_COUNT: number;

  private readonly route: ActivatedRoute;

  private readonly geographicalService: GeographicalService;
  private readonly crimeService: CrimeService;

  /**
   *
   */
  public city?: City;

  /**
   *
   */
  public crimes?: Crime[];

  /**
   *
   */
  public filteredCrimes?: Crime[];

  public page: number = 1;

  public categories?: Category[];

  multi = [
    {
      name: "Unconfirmed",
      series: [
        {
          name: new Date(2023, 6, 1).getTime(),
          value: 10
        },
        {
          name: new Date(2023, 6, 2).getTime(),
          value: 15
        },
        {
          name: new Date(2023, 6, 3).getTime(),
          value: 8
        }
      ]
    },
    {
      name: "Confirmed",
      series: [
        {
          name: new Date(2023, 6, 1).getTime(),
          value: 7
        },
        {
          name: new Date(2023, 6, 2).getTime(),
          value: 14
        },
        {
          name: new Date(2023, 6, 3).getTime(),
          value: 1
        }
      ]
    }
  ];

  public constructor(
    route: ActivatedRoute,
    geographicalService: GeographicalService,
    crimeService: CrimeService
  ) {
    this.PAGE_ITEM_COUNT = 5;
    this.PAGE_COUNT = 7;

    this.route = route;

    this.geographicalService = geographicalService;
    this.crimeService = crimeService;
  }

  public get start(): number {
    return (this.page - 1) * this.PAGE_ITEM_COUNT;
  }

  public get end(): number {
    return this.page * this.PAGE_ITEM_COUNT;
  }

  public get max(): number {
    if (this.filteredCrimes) {
      return this.filteredCrimes.length;
    }

    if (this.crimes) {
      return this.crimes.length;
    }

    return 0;
  }

  public ngOnInit(): void {
    const url: UrlSegment[] = this.route.snapshot.url;

    if (0 < url.length) {
      const lastSegment: string = url[url.length - 1].path;

      this.geographicalService.getCity(lastSegment).subscribe(it => this.city = it);
      this.crimeService.getCrimes(lastSegment).subscribe(it => {
        this.crimes = it.content;
        this.filteredCrimes = it.content;
      });
    }

    this.crimeService.getCategories().subscribe(it => this.categories = it);
  }

  public filterBy(category: Category) {
    this.filteredCrimes = [];
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
          this.filteredCrimes.push(crime);
          // console.log(category2.name)
        }
      }
    }
    // console.log(this.filterCrimes)
  }
}