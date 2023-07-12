import {NgModule} from "@angular/core"
import {RouterModule, Routes} from "@angular/router"
import {
  HomeComponent,
  PageNotFoundComponent,
  ResultDetailsComponent,
  StatisticsComponent
} from "./apperance"

const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    component: HomeComponent
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
    path: "**",
    pathMatch: "full",
    component: PageNotFoundComponent
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {
}
