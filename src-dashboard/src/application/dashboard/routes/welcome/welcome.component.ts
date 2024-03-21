import {Component, OnInit} from "@angular/core";
import {InMemoryStorage, StorageKey} from "../../../shared";
import {User} from "../../../core";
import {MatCard, MatCardContent} from "@angular/material/card";
import {TitleCasePipe} from "@angular/common";

@Component({
  selector: "app-welcome",
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    TitleCasePipe
  ],
  templateUrl: "./welcome.component.html",
  styleUrl: "./welcome.component.scss"
})
export class WelcomeComponent
  implements OnInit {

  private readonly inMemoryStorage: InMemoryStorage;

  private _user?: User;

  public constructor(
    inMemoryStorage: InMemoryStorage
  ) {
    this.inMemoryStorage = inMemoryStorage;
  }

  public get user(): User | undefined {
    if (this._user) {
      return this._user;
    }
    this._user = this.inMemoryStorage.get<User>(StorageKey.AUTHENTICATED_USER);
    return this._user;
  }

  public ngOnInit(): void {

  }
}
