import {Component, OnInit} from "@angular/core";
import {User, UserService} from "../core";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: [
    "./me.component.scss"
  ]
})
export class MeComponent
  implements OnInit {

  private readonly userService: UserService;
  public user?: User;

  public constructor(userService: UserService) {
    this.userService = userService;
  }

  public ngOnInit(): void {
    //
    this.userService.getMe().subscribe({
      next: (user: User): void => {
        this.user = user;
      },
      error: (): void => {
        console.debug("The user has not been authenticated.");
      }
    });
  }
}
