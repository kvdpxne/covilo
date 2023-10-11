import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../../../authentication";
import {User} from "../../model/user";
import {UserLifecycleService} from "../../service/user-lifecycle.service";

@Component({
  selector: "covilo-avatar",
  templateUrl: "./avatar.component.html"
})
export class AvatarComponent
  implements OnInit {

  private readonly userLifecycleService: UserLifecycleService;
  private readonly authenticationService: AuthenticationService;

  public me?: User;

  public constructor(
    userLifecycleService: UserLifecycleService,
    authenticationService: AuthenticationService
  ) {
    this.userLifecycleService = userLifecycleService;
    this.authenticationService = authenticationService;
  }

  public logout(): void {
    this.authenticationService.logout(true).subscribe();
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): User => this.me = user
    });
  }
}
