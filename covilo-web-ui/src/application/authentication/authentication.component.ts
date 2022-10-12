import { Component, OnInit } from "@angular/core"
import { FormControl, FormGroup } from "@angular/forms"
import { Router } from "@angular/router"
import { UserService } from "../application.module"

@Component({
  selector: "a-authentication",
  templateUrl: "./authentication.component.html"
})
export class AuthenticationComponent implements OnInit {

  formData: FormGroup

  log: boolean = false

  constructor(
    private router: Router,
    private userService: UserService
  ) {
    this.formData = new FormGroup({
      username: new FormControl()
    })
  }

  ngOnInit(): void {
  }

}
