import { ModuleWithProviders, NgModule } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { TranslateModule } from "@ngx-translate/core"
import { NgbDropdownModule } from "@ng-bootstrap/ng-bootstrap"

import { NavigationBackDirective } from "./directives/navigation-back.directive"

import {
    ApiHttpClientService, CardListComponent,
    InputComponent,
    TextFilterPipe
} from "./";
import { CardComponent } from './components';

@NgModule({
  declarations: [
    TextFilterPipe,
    InputComponent,
    NavigationBackDirective,
    CardComponent,
    CardListComponent
  ],
    exports: [
        InputComponent,
        NavigationBackDirective,
        CardListComponent
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
