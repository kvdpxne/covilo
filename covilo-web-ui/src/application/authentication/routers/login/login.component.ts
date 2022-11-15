import { Component, OnInit } from "@angular/core"
import { AuthenticationService, StorageService } from "../../"

@Component({
  selector: "a-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {

  form: any = {
    username: null,
    password: null
  }
  isLogged = false
  isLoginFailed = false
  errorMessage = ""
  roles: string[] = []

  constructor(
    private readonly authenticationService: AuthenticationService,
    private readonly storageService: StorageService
  ) {
  }

  ngOnInit(): void {
    if (this.storageService.isLogged()) {
      this.isLogged = true
    }
  }

  onSubmit(): void {
    const {username, password} = this.form

    this.authenticationService.login({email: username, password}).subscribe({
      next: (data: any) => {
        console.log(data)
        this.storageService.setUser(data)

        this.isLoginFailed = false
        this.isLogged = true
        this.roles = this.storageService.getUser()!!.roles
        this.reloadPage()
      },
      error: err => {
        this.errorMessage = err.error.message
        this.isLoginFailed = true
      }
    })
  }

  reloadPage(): void {
    window.location.reload()
  }
}
