import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

import {
  HomeComponent,
  InteractiveMapComponent,
  ReportComponent,
  ResultDetailsComponent,
  StatisticsComponent
} from "./appearance";
import {ComingSoonComponent, PageNotFoundComponent} from "./common";

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
    component: ReportComponent
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
