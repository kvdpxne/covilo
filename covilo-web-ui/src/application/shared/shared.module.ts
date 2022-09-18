import { ModuleWithProviders, NgModule } from "@angular/core"
import { CommonModule } from "@angular/common"
import { ApiHttpClientService } from "./services/api-http-client.service"
import { TextFilterPipe } from "./pipes/text-filter.pipe"
import { TranslateModule } from "@ngx-translate/core"
import { ContainerComponent, InputComponent } from "./components"
import { NgbDropdownModule } from "@ng-bootstrap/ng-bootstrap"
import { FormsModule } from "@angular/forms"

@NgModule({
  declarations: [
    TextFilterPipe,
    InputComponent,
    ContainerComponent
  ],
  exports: [
    InputComponent,
    ContainerComponent
  ],
  imports: [
    CommonModule,
    TranslateModule,
    NgbDropdownModule,
    FormsModule
  ]
})
export class SharedModule {

  static forRoot(): ModuleWithProviders<any> {
    return {
      ngModule: SharedModule,
      providers: [ApiHttpClientService]
    }
  }
}

// application services
export { ApiHttpClientService } from "./services/api-http-client.service"
export { TextFilterPipe } from "./pipes/text-filter.pipe"
