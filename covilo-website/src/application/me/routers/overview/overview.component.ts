import {Component, OnInit} from "@angular/core";
import {User, UserLifecycleService} from "../../../core";
import {TranslateModule} from "@ngx-translate/core";
import {DatePipe, LowerCasePipe} from "@angular/common";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "route-overview",
  templateUrl: "./overview.component.html",
  standalone: true,
  imports: [
    NgbTooltip,
    LowerCasePipe,
    DatePipe,
    TranslateModule
  ]
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
