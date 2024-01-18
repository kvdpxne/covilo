import {Component} from "@angular/core";
import {
  Book,
  Category,
  City,
  Country,
  CrimeService,
  GeolocationService,
  Province,
  ReportCrimeRequest,
  User
} from "../../../core";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";

interface ReportForm {

  city: FormControl<City | null>;

  datetime: FormControl<Date | null>;

  category: FormControl<Category | null>;

  title: FormControl<string | null>;

  description: FormControl<string | null>;
}

@Component({
  selector: "router-reporting",
  templateUrl: "./report.component.html"
})
export class ReportComponent {

  private readonly geographicalService: GeolocationService;

  private readonly crimeService: CrimeService;

  public categories?: Category[];

  public selectedCategories?: Category[];

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
    geographicalService: GeolocationService,
    crimeService: CrimeService,
    formBuilder: FormBuilder
  ) {
    this.geographicalService = geographicalService;
    this.crimeService = crimeService;

    this.formGroup = formBuilder.group<ReportForm>({
      city: new FormControl<City | null>(null),
      datetime: new FormControl<Date | null>(null),
      category: new FormControl<Category | null>(null),
      title: new FormControl<string | null>(null),
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
      next: (categories: Category[]) => {
        // this.categories = categories.map(value => new Category(
        //   value.identifier,
        //   value.name,
        //   value.classification
        // ))

        this.categories = categories
      }
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

  public get title(): FormControl<string | null> {
    return this.controls.title;
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

  public handleSelectAll(selectedOptions: Category[]): void {
    this.selectedCategories = selectedOptions;
  }

  /**
   * All available countries
   */
  private getAvailableCountries(): void {
    this.geographicalService.getCountries().subscribe({
      next: (value: Book<Country>): void => {
        this.countries = value.content.sort((a: Country, b: Country) => {
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
      next: (value: Book<Province>): void => {
        this.regions = value.content.sort((a: Province, b: Province) => {
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
      next: (value: Book<City>): void => {
        this.cities = value.content.sort((a, b) => {
          return a.nationalName.localeCompare(b.nationalName);
        });
      }
    });
  }

  /**
   *
   */
  public submit(): void {
    const datetime: Date | null = this.datetime.value;
    const city: City | null = this.city.value;
    const reporter: User | undefined = undefined;
    const categories: Category[] | undefined = this.selectedCategories;
    const title: string | null = this.title.value;
    const description: string | null = this.description.value;

    if (!datetime || !city || !categories || !description || !title) {
      return;
    }

    const request: ReportCrimeRequest = {
      title: title,
      description: description,
      categories: categories,
      time: datetime,
      reporter: reporter,
      place: city,
      confirmed: false
    };

    console.log(request);

    this.crimeService.report(request).subscribe({
      next: (value) => console.log(value)
    });
  }
}
