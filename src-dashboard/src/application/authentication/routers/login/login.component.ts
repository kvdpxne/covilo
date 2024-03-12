import {Component, inject, OnInit} from "@angular/core";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {
  FormBuilder,
  FormGroup,
  FormsModule, ReactiveFormsModule,
  Validators
} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {MatCheckbox} from "@angular/material/checkbox";
import {
  MatCard,
  MatCardContent,
  MatCardHeader, MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {LoginRequest} from "../../../core";
import {finalize} from "rxjs";

@Component({
  selector: "app-login",
  standalone: true,
  imports: [
    // Angular
    FormsModule,
    ReactiveFormsModule,

    // Angular Material
    MatButton,
    MatCheckbox,
    MatError,
    MatFormField,
    MatInput,
    MatCard,
    MatCardContent,
    MatCardTitle,
    MatCardHeader,
    MatCardSubtitle,
    MatLabel,
    RouterLink
  ],
  templateUrl: "./login.component.html",
  styleUrl: "./login.component.scss"
})
export class LoginComponent
  implements OnInit {

  private _loginForm?: FormGroup;

  /**
   * Initializes the login form in the class constructor.
   *
   * This constructor is responsible for creating and initializing the login form
   * using the provided FormBuilder instance. It sets up the form controls for
   * username and password with required validators.
   *
   * @param formBuilder The FormBuilder instance used to create the login form.
   *                    It should not be null or undefined.
   * @param authenticationService
   */
  public constructor(
    private readonly formBuilder: FormBuilder,
    private readonly authenticationService: AuthenticationService,
    private readonly router: Router
  ) {
  }

  /**
   * Creates the login form for the {@link LoginComponent}.
   *
   * Initializes the {@link _loginForm} {@link FormGroup} using the
   * {@link FormBuilder} service provided by the {@link LoginComponent}.
   * It defines form controls for username, password, and a checkbox for the
   * "Remember Me" feature.
   *
   * The {@link username} and {@link password} form controls are set as
   * required using {@link Validators.required}.
   * The {@link rememberMe} form control is initialized with a default
   * value of false.
   *
   * This method is private, as it is intended to be used internally by
   * the {@link LoginComponent}.
   */
  private createLoginForm(): void {
    const builder = this.formBuilder.nonNullable;

    this._loginForm = builder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
      rememberMe: [false]
    });
  }

  ngOnInit(): void {
    this.createLoginForm();
  }

  /**
   * Retrieves the login form {@link FormGroup} instance.
   *
   * If the {@link _loginForm} is not initialized, this method creates it by
   * calling the {@link createLoginForm} method.
   *
   * @returns The login form {@link FormGroup} instance.
   */
  get loginForm(): FormGroup {
    // Check if the loginForm is not initialized, then create it
    if (!this._loginForm) {
      this.createLoginForm();
    }

    // Return the loginForm FormGroup instance
    return this._loginForm!;
  }

  /**
   * Determines whether the username input field is invalid and has been
   * touched.
   *
   * This method checks the validity and touch status of the username form
   * control within the login form group. It returns true if the username
   * input field is invalid and has been touched by the user, otherwise
   * returns false.
   *
   * @returns A boolean value indicating whether the username input field is
   * invalid and touched.
   */
  get whetherUsernameIsInvalid(): boolean {
    // Retrieve the username form control
    const control = this.loginForm.controls["username"];

    // Return true if the username control is invalid and touched,
    // otherwise false
    return control.invalid && control.touched;
  }

  /**
   * Determines whether the password input field is invalid and has been
   * touched.
   *
   * This method checks the validity and touch status of the password form
   * control within the login form group. It returns true if the password
   * input field is invalid and has been touched by the user, otherwise
   * returns false.
   *
   * @returns A boolean value indicating whether the password input field is
   * invalid and touched.
   */
  get whetherPasswordIsInvalid(): boolean {
    // Retrieve the password form control
    const control = this.loginForm.controls["password"];

    // Return true if the username control is invalid and touched,
    // otherwise false
    return control.invalid && control.touched;
  }

  public submit(): void {
    if (!this._loginForm?.valid) {
      this._loginForm?.markAllAsTouched()
      return;
    }

    const username = this.loginForm.value.username;
    const password = this.loginForm.value.password;

    const request: LoginRequest = {
      email: username,
      password: password,
      rememberMe: false
    };

    this.authenticationService.login(request).subscribe(() => {
      this.router.navigateByUrl("/dashboard/welcome")
    })
  }
}
