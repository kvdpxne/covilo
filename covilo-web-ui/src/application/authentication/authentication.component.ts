import { Component } from "@angular/core"
import { Router } from "@angular/router"

import { UserService } from "../"

import {
  AuthenticationService,
  LoginCredentials,
  StorageService
} from "./"

@Component({
  selector: "a-authentication",
  templateUrl: "./authentication.component.html",
  styleUrls: [
    "./authentication.component.scss"
  ]
})
export class AuthenticationComponent {

  isLogged: boolean = false
  roles: string[] = []

  activeComponent: any

  constructor(
    private router: Router,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private storageService: StorageService
  ) {

  }

  onActivate(component: any): void {
    console.log(component)
    if (this.storageService.isLogged()) {
      this.isLogged = true
      this.roles = ["USER"]
    }

    this.activeComponent = component
  }

  onSubmit() {
    this.activeComponent.loginCredentialsEvent.subscribe({
      next: (value: LoginCredentials) => {
        console.log(value)
        this.authenticationService.login(value)
      }
    })
  }
}
