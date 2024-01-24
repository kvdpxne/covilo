import {ModuleWithProviders, NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";

import {
  ApiHttpClientService,
  AvatarImageComponent,
  BreadcrumbComponent,
  InputSelectComponent,
  NavigationBackDirective,
  TextFilterPipe
} from "./";
import {InputMultipleSelectComponent} from "./components";

@NgModule({
  exports: [
    AvatarImageComponent,
    BreadcrumbComponent,
    InputSelectComponent,
    InputMultipleSelectComponent,
    NavigationBackDirective
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    NgbDropdownModule,
    AvatarImageComponent,
    BreadcrumbComponent,
    InputSelectComponent,
    InputMultipleSelectComponent,
    NavigationBackDirective,
    TextFilterPipe
  ]
})
export class SharedModule {

  static forRoot(): ModuleWithProviders<any> {
    return {
      ngModule: SharedModule,
      providers: [ApiHttpClientService]
    };
  }
}
