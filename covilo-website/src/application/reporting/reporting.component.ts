import {Component} from "@angular/core";
import {City, Country, CrimeService, GeographicalService, Province, User} from "../core";
import {ActivatedRoute} from "@angular/router";
import {Continent} from "../core/models/continent";
import {Category} from "../core/models/category";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ReportCrimeRequest} from "./playload/report-crime-request";

interface ReportForm {

  city: FormControl<City | null>;

  datetime: FormControl<Date | null>;

  category: FormControl<Category | null>;

  description: FormControl<string | null>;
}

@Component({
  selector: "reporting-route",
  templateUrl: "./reporting.component.html",
  styleUrls: ["./reporting.component.scss"]
})
export class ReportingComponent {

  private readonly geographicalService: GeographicalService;

  private readonly crimeService: CrimeService;

  categories?: Category[];

  // available countries
  countries?: Country[];

  // available regions by country
  regions?: Province[];

  // available cities by region
  cities?: City[];

  // key of the selected country
  currentCountry?: Country;

  // key of the selected region
  currentProvince?: Province;

  // key of the selected city
  currentCity?: City;

  private formGroup: FormGroup<ReportForm>;

  constructor(
    route: ActivatedRoute,
    geographicalService: GeographicalService,
    crimeService: CrimeService,

    formBuilder: FormBuilder
  ) {
    this.geographicalService = geographicalService;
    this.crimeService = crimeService;

    this.formGroup = formBuilder.group<ReportForm>({
      city: new FormControl<City | null>(null),
      datetime: new FormControl<Date | null>(null),
      category: new FormControl<Category | null>(null),
      description: new FormControl<string | null>(null)
    });

    // const segments: UrlSegment[] = route.snapshot.url;
    // if (0 < segments.length) {
    //   const last: string = segments[segments.length - 1].path
    //   this.geographicalService.getCity(last).subscribe({
    //     next: (city: City): void => {
    //       this.currentCity = city;
    //     }
    //   });
    // }

    this.getAvailableCountries();
    this.crimeService.getCategories().subscribe({
      next: (categories: Category[]) => this.categories = categories
    });
  }

  private get controls() {
    return this.formGroup.controls;
  }

  public get city(): FormControl<City | null> {
    return this.controls.city;
  }

  public get datetime(): FormControl<Date | null> {
    return this.controls.datetime;
  }

  public get category(): FormControl<Category | null> {
    return this.controls.category;
  }

  public get description(): FormControl<string | null> {
    return this.controls.description;
  }

  /**
   * Overwrite the country key and fetch all regions for it
   */
  setCountry(country: Country): void {
    this.currentCountry = country;
    this.getAvailableRegions();
  }

  /**
   * Overwrite the region key and fetch all cities for it
   */
  setRegion(region: Province): void {
    this.currentProvince = region;
    this.getAvailableCities();
  }

  /**
   * Overwrite the city key
   */
  setCity(city: City): void {
    this.currentCity = city;
    this.city.setValue(city);
  }

  canEnabled(value?: any): boolean {
    return null == value;
  }

  /**
   * All available countries
   */
  private getAvailableCountries(): void {
    this.geographicalService.getCountriesByContinent(Continent.EUROPE).subscribe({
      next: (value: Country[]): void => {
        this.countries = value.sort((a: Country, b: Country) => {
          return a.name.localeCompare(b.name);
        });
      }
    });
  }

  /**
   * All available regions for selected country
   */
  private getAvailableRegions(): void {
    if (!this.currentCountry) {
      throw new Error("Cannot get regions if country is undefined.");
    }
    this.geographicalService.getProvincesByCountry(this.currentCountry).subscribe({
      next: (value: Province[]): void => {
        this.regions = value.sort((a: Province, b: Province) => {
          return a.nationalName.localeCompare(b.nationalName);
        });
      }
    });
  }

  /**
   * All available cities for selected region
   */
  private getAvailableCities(): void {
    if (!this.currentProvince) {
      throw new Error("Cannot get cities if country or region is undefined.");
    }
    this.geographicalService.getCitiesByProvince(this.currentProvince).subscribe({
      next: (value: City[]): void => {
        this.cities = value.sort((a, b) => {
          return a.nationalName.localeCompare(b.nationalName);
        });
      }
    });
  }

  /**
   *
   */
  public submit(): void {
    const datetime: Date | null = this.datetime.value
    const city: City | null = this.city.value;
    const reporter: User | null = null;
    const category: Category | null = this.category.value;
    const description: string | null = this.description.value;

    if (!datetime || !city || !category || !description) {
      return
    }

    const request: ReportCrimeRequest = {
      datetime: datetime,
      city: city,
      reporter: reporter,
      category: category,
      description: description,
      confirmed: false
    };

    console.log("log request")
    console.log(request)

    this.crimeService.report(request);
  }
}
