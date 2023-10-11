import {Component, OnInit} from "@angular/core";
import {User, UserLifecycleService} from "../../../core";

@Component({
  selector: "route-overview",
  templateUrl: "./overview.component.html"
})
export class OverviewComponent
  implements OnInit {

  private readonly userLifecycleService: UserLifecycleService;
  public user?: User;

  public constructor(userLifecycleService: UserLifecycleService) {
    this.userLifecycleService = userLifecycleService;
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): User => this.user = user
    });
  }
}
