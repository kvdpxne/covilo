import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {City, Crime, CrimeService} from "src/application/core";
import {Category, GeolocationService} from "../../../core";
import {TranslateModule} from "@ngx-translate/core";
import {CardListComponent, FilterByCategoryComponent} from "../../component";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "router-result-details",
  templateUrl: "./result-details.component.html",
  standalone: true,
  imports: [CardListComponent, NgbPagination, FilterByCategoryComponent, TranslateModule]
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

  private readonly geographicalService: GeolocationService;
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
  public selectedCategories: Category[] = [];

  public constructor(
    route: ActivatedRoute,
    geographicalService: GeolocationService,
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

  public handleChanges(categories: Category[]): void {
    this.selectedCategories = categories;
    console.log(this.selectedCategories);
  }

  public filter(): void {
    if (!this.crimes?.length) {
      return;
    }
    const categories: Category[] | undefined = this.selectedCategories;
    const size: number = categories?.length;
    if (!size) {
      this.filteredCrimes = this.crimes;
      return;
    }
    this.filteredCrimes = this.crimes.filter((crime: Crime): boolean => {
      let i: number = 0;
      return crime.categories.some((category: Category): boolean =>
        categories.some((selectedCategory: Category): boolean => {
          if (category.identifier === selectedCategory.identifier) {
            i++;
          }
          return size === i;
        })
      );
    });
  }
}
