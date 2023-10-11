import {Component} from "@angular/core";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {Router} from "@angular/router";
import {SignupRequest} from "../../../core";

interface SignupForm {

  email: FormControl<string | null>;

  password: FormControl<string | null>;

  confirmPassword: FormControl<string | null>;

  privacyPolicy: FormControl<boolean | null>;
}

@Component({
  selector: "router-authentication-signup",
  templateUrl: "./signup.component.html"
})
export class SignupComponent {

  /**
   * A group of control forms needed to hold user authentication information.
   */
  private readonly formGroup: FormGroup<SignupForm>;

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
    this.formGroup = formBuilder.group<SignupForm>({
      email: new FormControl<string | null>(null),
      password: new FormControl<string | null>(null),
      confirmPassword: new FormControl<string | null>(null),
      privacyPolicy: new FormControl<boolean | null>(false)
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

  public get confirmPassword(): FormControl<string | null> {
    return this.controls.confirmPassword;
  }

  public get privacyPolicy(): FormControl<boolean | null> {
    return this.controls.privacyPolicy;
  }

  public isEmailValid(): boolean {
    const control: AbstractControl<string | null, string> = this.email;
    return control.invalid && (control.dirty || control.touched);
  }

  public isPasswordValid(): boolean {
    const control: AbstractControl<string | null, string> = this.password;
    return control.invalid && (control.dirty || control.touched);
  }

  public isConfirmPasswordValid(): boolean {
    const control: AbstractControl<string | null, string> = this.confirmPassword;
    return control.invalid && (control.dirty || control.touched);
  }

  public isPrivacyPolicyValid(): boolean {
    const control: AbstractControl<boolean | null, boolean> = this.privacyPolicy;
    return control.invalid && (control.dirty || control.touched);
  }

  public submit(): void {
    //
    const email: string | null = this.email.value;
    const password: string | null = this.password.value;
    const confirmPassword: string | null = this.password.value;
    const privacyPolicy: boolean | null = this.privacyPolicy.value;

    // Checks if the values are not null or empty.
    if (!email || !password || !confirmPassword || !privacyPolicy) {
      return;
    }

    //
    const request: SignupRequest = {
      firstName: "Sergio",
      lastName: "Drugi",
      email: email,
      password: password,
      confirmPassword: confirmPassword,
      privacyPolicy: privacyPolicy
    };

    this.authenticationService.signup(request).subscribe((): void => {
      this.router.navigate(["/"]).catch((reason: any): void => {
        console.error(reason);
      });
    });
  }
}
