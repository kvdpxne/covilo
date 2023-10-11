import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {
  ComingSoonComponent,
  HomeComponent, InteractiveMapComponent,
  PageNotFoundComponent,
  ResultDetailsComponent,
  StatisticsComponent
} from "./apperance";
import {ReportingComponent} from "./reporting/reporting.component";

const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    component: HomeComponent
  },
  {
    path: "interactive-map",
    component: InteractiveMapComponent
  },
  {
    path: "result-details/:city",
    component: ResultDetailsComponent
  },
  {
    path: "statistics",
    component: StatisticsComponent
  },
  {
    path: "reporting",
    component: ReportingComponent
  },
  {
    path: "coming-soon",
    component: ComingSoonComponent
  },
  {
    path: "**",
    pathMatch: "full",
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {
}
