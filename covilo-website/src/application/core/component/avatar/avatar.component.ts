import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../../../authentication";
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {ApiHttpClientService} from "../../../shared";

@Component({
  selector: "covilo-avatar",
  templateUrl: "./avatar.component.html",
  styleUrls: ["./avatar.component.scss"]
})
export class AvatarComponent implements OnInit {

  private readonly userService: UserService;
  private readonly authenticationService: AuthenticationService;

  private readonly httpClientService: ApiHttpClientService;

  public me?: User;

  public avatar?: string;

  public constructor(
    userService: UserService,
    authenticationService: AuthenticationService,
    httpClientService: ApiHttpClientService
  ) {
    this.userService = userService;
    this.authenticationService = authenticationService;
    this.httpClientService = httpClientService;
  }

  public avatarReference(): void {
    this.userService.viewAvatar().subscribe(it => this.avatar = it);
  }

  public logout(): void {
    this.authenticationService.logout(true).subscribe();
  }

  public ngOnInit(): void {
    this.userService.getMe().subscribe((user: User): void => {
      this.me = user;
    });
    this.avatarReference();
    console.log(this.avatar);
  }
}
