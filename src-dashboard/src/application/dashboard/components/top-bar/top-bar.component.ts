import { Component } from '@angular/core';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {
  TopBarSearchComponent
} from "../top-bar-search/top-bar-search.component";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatAnchor, MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {
  AuthenticationStrategy,
  TokenAuthenticationStrategy
} from "../../../authentication";

@Component({
  selector: 'app-top-bar',
  standalone: true,
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    TopBarSearchComponent,
    MatMenuTrigger,
    MatButton,
    MatMenu,
    MatMenuItem,
    MatAnchor,
    MatIcon
  ],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss'
})
export class TopBarComponent {

  private readonly authenticationStrategy: AuthenticationStrategy<any>;

  public constructor(
    // TODO required type any
    authenticationStrategy: TokenAuthenticationStrategy
  ) {
    this.authenticationStrategy = authenticationStrategy;
  }

  public handleLogout(): void {
    this.authenticationStrategy.doLogout();
  }
}
