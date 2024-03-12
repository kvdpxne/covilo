import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthenticationRoutingModule} from "./authentication-routing.module";
import {
  AUTHENTICATION_INTERCEPTOR_PROVIDER
} from "./intercreptors/authentication-interceptor-provider";

@NgModule({
  providers: [
    AUTHENTICATION_INTERCEPTOR_PROVIDER
  ],
  declarations: [
  ],
  imports: [
    CommonModule,
    AuthenticationRoutingModule
  ]
})
export class AuthenticationModule { }
