import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild
} from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import {
  Crime,
  CrimeClassification,
  CrimeClassificationService,
  CrimeService,
  LocationCity,
  LocationCityService
} from "../../../core/core.module";
import { BaseChartDirective } from "ng2-charts";
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from "chart.js";

@Component({
  selector: "result-details",
  templateUrl: "./result-details.component.html"
})
export class ResultDetailsComponent implements AfterViewInit, OnInit {

  @ViewChild(BaseChartDirective)
  chart?: BaseChartDirective

  //
  city?: LocationCity

  // All available crimes for the city
  crimes?: Array<Crime>

  // All available crime classifications
  classifications?: Array<CrimeClassification>

  //
  comments?: Array<Comment>

  constructor(private route: ActivatedRoute,
              private cityService: LocationCityService,
              private crimeService: CrimeService,
              private classificationService: CrimeClassificationService
  ) {
  }

  private getCity(p0: string, p1: string, p2: string): void {
    this.cityService.getOne(p0, p1, p2).subscribe({
      next: value => this.city = value,
      complete: () => console.debug(this.city)
    })
  }

  private getCrimes(p0: string, p1: string, p2: string): void {
    this.crimeService.getAll(p0, p1, p2).subscribe({
      next: value => this.crimes = value,
      complete: () => console.debug(this.crimes)
    })
  }

  private getClassifications(): void {
    this.classificationService.getAll().subscribe({
      next: value => this.classifications = value,
      complete: () => console.debug(this.classifications)
    })
  }

  public barChartOptions: ChartConfiguration['options'] = {
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
        display: true,
      },
    }
  };
  public barChartType: ChartType = 'bar';
  public barChartPlugins = [

  ];

  public barChartData: ChartData<'bar'> = {
    labels: [ 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday' ],
    datasets: [
      { data: [ 97, 31, 19, 60, 85, 57, 27 ], label: 'Series A' },
      { data: [ 75, 76, 49, 69, 84, 33, 60 ], label: 'Series B' },
      { data: [ 91, 50, 15, 67, 22, 98, 60 ], label: 'Series C' },
      { data: [ 20, 62, 78, 96, 21, 88, 65 ], label: 'Series D' }
    ]
  };

  // events
  public chartClicked({ event, active }: { event?: ChartEvent, active?: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event?: ChartEvent, active?: {}[] }): void {
    console.log(event, active);
  }

  public randomize(): void {
    // Only Change 3 values
    this.barChartData.datasets[0].data = [
      Math.round(Math.random() * 100),
      59,
      80,
      Math.round(Math.random() * 100),
      56,
      Math.round(Math.random() * 100),
      40 ];

    this.chart?.update();
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
    this.getCity(country, region, city)
    this.getCrimes(country, region, city)
    this.getClassifications()
  }
}
