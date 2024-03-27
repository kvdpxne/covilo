import {Component, OnInit} from "@angular/core";
import {Page, User, UserService} from "../../../core";
import {
  MatCell, MatCellDef,
  MatColumnDef, MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable
} from "@angular/material/table";

@Component({
  selector: "app-user-management",
  standalone: true,
  imports: [
    MatTable,
    MatColumnDef,
    MatHeaderCellDef,
    MatHeaderCell,
    MatCell,
    MatCellDef,
    MatHeaderRowDef,
    MatHeaderRow,
    MatRow,
    MatRowDef
  ],
  templateUrl: "./user-management.component.html",
})
export class UserManagementComponent
  implements OnInit {

  displayedColumns: string[] = ['identifier', 'email', 'first name', 'last' +
  ' name'];

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
