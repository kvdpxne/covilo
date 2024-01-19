import {Component, OnInit} from "@angular/core";
import {User} from "../../model/user";
import {UserLifecycleService} from "../../service/user-lifecycle.service";
import {ROUTE_LINKS} from "../../../me/me-routing.module";
import {UserAuthenticationService} from "../../service/user-authentication.service";

@Component({
  selector: "covilo-avatar",
  templateUrl: "./avatar.component.html"
})
export class AvatarComponent
  implements OnInit {

  private readonly userLifecycleService: UserLifecycleService;
  private readonly authenticationService: UserAuthenticationService;

  public me?: User;

  public constructor(
    userLifecycleService: UserLifecycleService,
    authenticationService: UserAuthenticationService
  ) {
    this.userLifecycleService = userLifecycleService;
    this.authenticationService = authenticationService;
  }

  public logout(): void {
    this.authenticationService.logout(true);
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): User => this.me = user
    });
  }

  protected readonly ROUTE_LINKS = ROUTE_LINKS;
}
