import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MeComponent} from './me.component';
import {MeRoutingModule} from "./me-routing.module";

@NgModule({
  declarations: [
    MeComponent
  ],
  imports: [
    CommonModule,
    MeRoutingModule
  ]
})
export class MeModule { }
