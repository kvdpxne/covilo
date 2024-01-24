import {Component, OnInit} from "@angular/core";
import {User, UserLifecycleService} from "../core";
import {RouterOutlet} from "@angular/router";
import {SideNavigationBarComponent} from "./component";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  standalone: true,
  imports: [
    SideNavigationBarComponent,
    RouterOutlet
  ]
})
export class MeComponent
  implements OnInit {

  private readonly userLifecycleService: UserLifecycleService;
  public user?: User;

  public constructor(userLifecycleService: UserLifecycleService) {
    this.userLifecycleService = userLifecycleService;
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): User => this.user = user,
      error: (): void => console.debug("The user has not been authenticated.")
    });
  }
}
