import { Component, OnInit } from "@angular/core"
import { AuthenticationService, StorageService } from "../../index"
import { Router } from "@angular/router"
import { throwError } from "rxjs"

@Component({
  selector: "a-logout",
  templateUrl: "./logout.component.html"
})
export class LogoutComponent implements OnInit {

  constructor(
    private readonly router: Router,
    private readonly authenticationService: AuthenticationService,
    private readonly storageService: StorageService
  ) {
  }

  ngOnInit(): void {
    const email = this.storageService.getUser()!!.email
    this.authenticationService.logout(email).subscribe(() => {
      this.storageService.clean()
      this.router.navigateByUrl("/").catch(error => throwError(() => error))
      console.log("successful logout.")
    })
  }
}
