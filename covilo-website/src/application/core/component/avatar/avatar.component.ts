import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../../../authentication";
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";

@Component({
  selector: "covilo-avatar",
  templateUrl: "./avatar.component.html",
  styleUrls: ["./avatar.component.scss"]
})
export class AvatarComponent implements OnInit {

  private readonly userService: UserService;
  private readonly authenticationService: AuthenticationService;

  public me?: User;

  public constructor(
    userService: UserService,
    authenticationService: AuthenticationService,
  ) {
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  public logout(): void {
    this.authenticationService.logout(true).subscribe();
  }

  public ngOnInit(): void {
    this.userService.getMe().subscribe((user: User): void => {
      this.me = user;
    });
  }
}
