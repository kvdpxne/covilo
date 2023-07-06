import { ModuleWithProviders, NgModule } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { TranslateModule } from "@ngx-translate/core"
import { NgbDropdownModule } from "@ng-bootstrap/ng-bootstrap"

import { NavigationBackDirective } from "./directives/navigation-back.directive"

import {
  ApiHttpClientService,
  InputComponent,
  TextFilterPipe
} from "./"

@NgModule({
  declarations: [
    TextFilterPipe,
    InputComponent,
    NavigationBackDirective
  ],
  exports: [
    InputComponent,
    NavigationBackDirective
  ],
  imports: [
    CommonModule,
    TranslateModule.forChild(),
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