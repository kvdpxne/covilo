import {Component, OnInit} from "@angular/core";
import {User, UserService} from "../core";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: ["./me.component.scss"]
})
export class MeComponent implements OnInit {

  private readonly userService: UserService;
  public me?: User;

  constructor(userService: UserService) {
    this.userService = userService;
  }

  public ngOnInit(): void {
    this.userService.getMe().subscribe((user: User): void => {
      this.me = user;
    });
  }
}
