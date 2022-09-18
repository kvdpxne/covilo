import { NgModule } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule, ReactiveFormsModule } from "@angular/forms"
import { TranslateModule } from "@ngx-translate/core"
import { NgChartsModule } from "ng2-charts"
import { AppointmentRoutingModule } from "./appointment-routing.module"
import { CoreModule } from "../core/core.module"
import { SharedModule } from "../shared/shared.module"
import {
  AuthenticationComponent,
  HomeComponent,
  ResultDetailsComponent,
  ResultListComponent,
  StatisticsComponent
} from "./routes"

@NgModule({
  declarations: [
    AuthenticationComponent,
    HomeComponent,
    ResultDetailsComponent,
    ResultListComponent,
    StatisticsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    AppointmentRoutingModule,
    CoreModule,
    SharedModule,
    NgChartsModule
  ]
})
export class AppointmentModule {

}
