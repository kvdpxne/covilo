import {Component} from "@angular/core"
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {LoginRequest} from "../../../core/playload/login-request";
import {Router} from "@angular/router";
import {Token} from "../../../core/model/token";
import {throwError} from "rxjs";

interface LoginForm {

  email: FormControl<string | null>;
  password: FormControl<string | null>;
}

@Component({
  selector: "covilo-authentication-login",
  templateUrl: "./login.component.html"
})
export class LoginComponent {

  /**
   * A group of control forms needed to hold user authentication information.
   */
  private readonly formGroup: FormGroup<LoginForm>;

  private readonly router: Router;

  /**
   *
   */
  private readonly authenticationService: AuthenticationService;

  constructor(
    formBuilder: FormBuilder,
    router: Router,
    authenticationService: AuthenticationService
  ) {
    // Initializes the standard form group with the FormGroup constructor
    // needed to hold the user authentication information.
    this.formGroup = formBuilder.group<LoginForm>({
      email: new FormControl<string | null>(null),
      password: new FormControl<string | null>(null)
    }, {
      validators: Validators.required
    });
    //
    this.router = router;
    // Initiates the standard services.
    this.authenticationService = authenticationService;
  }

  private get controls() {
    return this.formGroup.controls;
  }

  public get email(): FormControl<string | null> {
    return this.controls.email;
  }

  public get password(): FormControl<string | null> {
    return this.controls.password;
  }

  public isEmailValid(): boolean {
    const control: AbstractControl<string | null, string> = this.email;
    return control.invalid && (control.dirty || control.touched);
  }

  public isPasswordValid(): boolean {
    const control: AbstractControl<string | null, string> = this.password;
    return control.invalid && (control.dirty || control.touched);
  }

  public submit(): void {
    //
    const email: string | null = this.email.value;
    const password: string | null = this.password.value;

    // Checks if the values are not null or empty.
    if (!email || !password) {
      return;
    }

    const request: LoginRequest = {
      email: email,
      password: password
    };

    this.authenticationService.login(request).subscribe((): void => {
      // noinspection JSIgnoredPromiseFromCall
      this.router.navigate(["/"])
    });
  }
}
