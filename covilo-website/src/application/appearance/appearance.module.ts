import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";
import {NgxChartsModule} from "@swimlane/ngx-charts";
import {SharedModule} from "../shared";
import {CardComponent, CardListComponent, CardTagComponent, CardTagListComponent} from "./component";
import {HomeComponent, InteractiveMapComponent, ReportComponent, ResultDetailsComponent, StatisticsComponent} from "./router";

@NgModule({
  declarations: [
    CardComponent,
    CardListComponent,
    CardTagComponent,
    CardTagListComponent,
    HomeComponent,
    InteractiveMapComponent,
    ReportComponent,
    ResultDetailsComponent,
    StatisticsComponent
  ],
  exports: [
    CardListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    TranslateModule.forChild(),
    NgbPagination,
    NgxChartsModule,

    SharedModule
  ]
})
export class AppearanceModule {
}
