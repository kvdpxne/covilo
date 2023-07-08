import {Component} from "@angular/core";
import {AuthenticationService} from "../../../authentication";

@Component({
  selector: "covilo-avatar",
  templateUrl: "./avatar.component.html",
  styleUrls: ["./avatar.component.scss"]
})
export class AvatarComponent {

  private readonly authenticationService: AuthenticationService;

  public constructor(authenticationService: AuthenticationService) {
    this.authenticationService = authenticationService;
  }

  public logout(): void {
    this.authenticationService.logout().subscribe({
      complete: () => {
        location.reload();
        console.debug("Successfully logged out.");
      }
    });
  }
}
