import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MeComponent} from './me.component';
import {MeRoutingModule} from "./me-routing.module";
import { OverviewComponent } from './router/overview/overview.component';
import { SideNavigationBarComponent } from './component/side-navigation-bar/side-navigation-bar.component';
import { SideNavigationComponent } from './component/side-navigation/side-navigation.component';
import {SharedModule} from "../shared";

@NgModule({
  declarations: [
    MeComponent,
    OverviewComponent,
    SideNavigationBarComponent,
    SideNavigationComponent
  ],
  imports: [
    CommonModule,
    MeRoutingModule,
    SharedModule
  ]
})
export class MeModule { }
