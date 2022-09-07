import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { AppointmentRoutingModule } from './appointment-routing.module';
import { CoreModule } from "../core/core.module";
import { SharedModule } from "../shared/shared.module";
import { HomeComponent, ResultDetailsComponent, } from "./routes";
import { StatisticsComponent } from './routes';
import { NgChartsModule } from "ng2-charts";

@NgModule({
  declarations: [
    HomeComponent,
    ResultDetailsComponent,
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
