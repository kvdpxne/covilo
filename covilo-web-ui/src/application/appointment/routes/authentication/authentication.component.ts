import { Component, OnInit } from "@angular/core"
import { UserService } from "../../../core"
import { FormBuilder, FormGroup, Validators } from "@angular/forms"
import { Router } from "@angular/router"

@Component({
  selector: "a-authentication",
  templateUrl: "./authentication.component.html"
})
export class AuthenticationComponent implements OnInit {

  formGroup: FormGroup

  log: boolean = false

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {
    this.formGroup = this.formBuilder.group({
      email: ["", Validators.required],
      password: ["", Validators.required]
    })
  }

  ngOnInit(): void {
  }

}
