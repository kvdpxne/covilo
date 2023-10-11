import {Component, OnInit} from "@angular/core";
import {User, UserLifecycleService, UserService} from "../core";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html"
})
export class MeComponent
  implements OnInit {

  private readonly userService: UserService;
  private readonly userLifecycleService: UserLifecycleService;

  public user?: User;

  public constructor(
    userService: UserService,
    userLifecycleService: UserLifecycleService
  ) {
    this.userService = userService;
    this.userLifecycleService = userLifecycleService;
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): User => this.user = user,
      error: (): void => console.debug("The user has not been authenticated.")
    });
  }
}
