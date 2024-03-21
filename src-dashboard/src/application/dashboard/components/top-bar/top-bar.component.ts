import {Component} from "@angular/core";
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
import {InMemoryStorage, Storage, StorageKey} from "../../../shared";
import {User} from "../../../core";
import {TitleCasePipe} from "@angular/common";

@Component({
  selector: "app-top-bar",
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
    MatIcon,
    TitleCasePipe
  ],
  templateUrl: "./top-bar.component.html",
  styleUrl: "./top-bar.component.scss"
})
export class TopBarComponent {

  private readonly authenticationStrategy: AuthenticationStrategy<any>;
  private readonly storage: Storage;

  public constructor(
    // TODO required type any
    authenticationStrategy: TokenAuthenticationStrategy,
    inMemoryStorage: InMemoryStorage
  ) {
    this.authenticationStrategy = authenticationStrategy;
    this.storage = inMemoryStorage;
  }

  public get fullName(): string {
    const user = this.storage.get<User>(StorageKey.AUTHENTICATED_USER);
    return user
      ? `${user.firstName} ${user.lastName}`
      : "undefined name";
  }

  public handleLogout(): void {
    this.authenticationStrategy.doLogout();
  }
}
