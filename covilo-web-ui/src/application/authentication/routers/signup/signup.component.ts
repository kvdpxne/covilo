import { Component, OnInit } from "@angular/core"

@Component({
  selector: "a-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.scss"]
})
export class SignupComponent implements OnInit {
  isLogged: boolean = false

  form: any = {
    email: null,
    password: null,
    confirmPassword: null,
    fsf: null
  }

  constructor() {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    return false
  }
}
