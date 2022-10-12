import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"
import {
  HomeComponent,
  PageNotFoundComponent,
  ResultDetailsComponent,
  ResultListComponent,
  StatisticsComponent
} from "./apperance"

const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    component: HomeComponent
  },
  {
    path: "result-details/:country/:region/:city",
    component: ResultDetailsComponent
  },
  {
    path: "result-list/:country/:region",
    component: ResultListComponent
  },
  {
    path: "statistics",
    component: StatisticsComponent
  },
  // {
  //   path: "**",
  //   pathMatch: "full",
  //   component: PageNotFoundComponent
  // }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {
}
