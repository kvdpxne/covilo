import {ModuleWithProviders, NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";

import {
  ApiHttpClientService,
  AvatarImageComponent,
  BreadcrumbComponent,
  CardComponent,
  CardListComponent,
  InputComponent,
  NavigationBackDirective,
  TextFilterPipe
} from "./";

@NgModule({
  declarations: [
    AvatarImageComponent,
    BreadcrumbComponent,
    CardComponent,
    CardListComponent,
    InputComponent,

    NavigationBackDirective,
    TextFilterPipe
  ],
  exports: [
    AvatarImageComponent,
    BreadcrumbComponent,
    CardComponent,
    CardListComponent,
    InputComponent,

    NavigationBackDirective
  ],
  imports: [
    CommonModule,
    FormsModule,
    TranslateModule.forChild(),
    NgbDropdownModule
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
