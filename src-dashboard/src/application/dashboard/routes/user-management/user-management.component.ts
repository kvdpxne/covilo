import {Component, OnInit} from "@angular/core";
import {Page, User, UserService} from "../../../core";

@Component({
  selector: "app-user-management",
  standalone: true,
  imports: [],
  templateUrl: "./user-management.component.html",
})
export class UserManagementComponent
  implements OnInit {

  private readonly userService: UserService;

  /**
   *
   */
  public users?: Page<User>;

  public constructor(
    userService: UserService
  ) {
    this.userService = userService;
  }


  public ngOnInit(): void {
    this.userService.getUsers().subscribe(users => this.users = users);
  }
}
