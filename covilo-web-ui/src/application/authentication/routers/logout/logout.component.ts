import { Component, OnInit } from "@angular/core"
import { Router } from "@angular/router"
import { AuthenticationService } from "../../authentication.module"

@Component({
  selector: "a-logout",
  templateUrl: "./logout.component.html"
})
export class LogoutComponent implements OnInit {

  constructor(
    private readonly router: Router,
    private readonly authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.logout()
    this.router.navigateByUrl("/")
  }
}
