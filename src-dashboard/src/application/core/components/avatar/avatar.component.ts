import {Component, OnInit} from "@angular/core";
import {User} from "../../models/user";
import {UserLifecycleService} from "../../services/user-lifecycle.service";
import {ROUTE_LINKS} from "../../../me/me-routing.module";
import {AuthenticationService} from "../../../authentication/services/authentication.service";
import { TranslateModule } from "@ngx-translate/core";
import { RouterLink } from "@angular/router";
import { NgFor } from "@angular/common";
import { AvatarImageComponent } from "../../../shared/components/avatar-image/avatar-image.component";
import { NgbDropdown, NgbDropdownToggle, NgbDropdownMenu, NgbDropdownItem, NgbDropdownButtonItem } from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: "covilo-avatar",
    templateUrl: "./avatar.component.html",
    standalone: true,
    imports: [NgbDropdown, NgbDropdownToggle, AvatarImageComponent, NgbDropdownMenu, NgFor, NgbDropdownItem, RouterLink, NgbDropdownButtonItem, TranslateModule]
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
    this.authenticationService.logout(true);
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): User => this.me = user
    });
  }

  protected readonly ROUTE_LINKS = ROUTE_LINKS;
}
