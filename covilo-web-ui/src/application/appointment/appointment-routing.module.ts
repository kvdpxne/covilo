import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"
import {
  AuthenticationComponent,
  HomeComponent,
  ResultDetailsComponent,
  ResultListComponent,
  StatisticsComponent
} from "./routes"

const routes: Routes = [
  {
    path: "authentication/:type",
    component: AuthenticationComponent
  },
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
  }
]

const routerParams = new Map<string, string[]>([
  ["authentication", ["type"]],
  ["result-details", ["country", "region", "city"]]
])

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppointmentRoutingModule {
}
