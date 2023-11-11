import {Component} from "@angular/core";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {SignupRequest} from "../../../core";
import {NavigationService, StorageService} from "../../../shared";
import {SignupStage} from "./signup-stage";
import {SignupForm, SignupFormGroup} from "./signup-form";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: "router-authentication-signup",
  templateUrl: "./signup.component.html"
})
export class SignupComponent {

  /**
   * A group of control forms needed to hold user authentication information.
   */
  public readonly signupForm: SignupFormGroup;

  private readonly navigationService: NavigationService;

  private readonly storageService: StorageService;

  /**
   *
   */
  private readonly authenticationService: AuthenticationService;

  protected readonly stage = SignupStage;

  /**
   * Current stage of user registration.
   */
  public currentStage: SignupStage;

  private translateService: TranslateService;

  /**
   *
   */
  public readonly lastStage: SignupStage;

  public test: FormGroup;

  public constructor(
    formBuilder: FormBuilder,
    translateService: TranslateService,
    navigationService: NavigationService,
    storageService: StorageService,
    authenticationService: AuthenticationService
  ) {

    this.test = formBuilder.group({
      qualification: formBuilder.group({
        birthDate: [new Date()]
      }),
      basicData: formBuilder.group({
        firstName: [""],
        lastName: [""]
      }),
      authenticationData: formBuilder.group({
        email: [""],
        password: [""],
        confirmPassword: [""],
        privacyPolicy: [""]
      })
    }, {
      validators: Validators.required
    });

    // Initializes the standard form group with the FormGroup constructor
    // needed to hold the user authentication information.
    this.signupForm = formBuilder.group<SignupForm>({
      birthDate: new FormControl<Date | null>(null),
      firstName: new FormControl<string | null>(null),
      lastName: new FormControl<string | null>(null),
      email: new FormControl<string | null>(null),
      password: new FormControl<string | null>(null),
      confirmPassword: new FormControl<string | null>(null),
      privacyPolicy: new FormControl<boolean>(false)
    }, {
      validators: Validators.required
    });

    this.translateService = translateService;

    //
    this.navigationService = navigationService;
    this.storageService = storageService;
    this.authenticationService = authenticationService;

    //
    this.currentStage = this.stage.QUALIFICATION;
    this.lastStage = Object.keys(this.stage).length;

    // Only for application development.
    this.currentStage = this.lastStage;
  }

  public translate(key: string): string {
    return this.translateService.instant("authentication.signup.".concat(key));
  }

  public get qualification(): FormGroup {
    return this.test.get("qualification") as FormGroup;
  }

  public get basicData(): FormGroup {
    return this.test.get("basicData") as FormGroup;
  }

  public get authenticationData(): FormGroup {
    return this.test.get("authenticationData") as FormGroup;
  }

  /**
   *
   */
  public nextStage(): void {
    const nextStage: number = 1 + this.currentStage;
    this.currentStage = nextStage >= this.lastStage
      ? this.lastStage
      : nextStage;
  }

  /**
   *
   */
  public showStage(stage: SignupStage): boolean {
    return this.currentStage >= stage;
  }

  private get controls() {
    return this.signupForm.controls;
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

  public isPrivacyPolicyValid(): boolean {
    const control: AbstractControl<boolean | null, boolean> = this.privacyPolicy;
    return control.invalid && (control.dirty || control.touched);
  }

  public submit(): void {
    //
    const email: string | null = this.email.value;
    const password: string | null = this.password.value;
    const confirmPassword: string | null = this.confirmPassword.value;
    const privacyPolicy: boolean | null = this.privacyPolicy.value;

    console.log(password);

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
      this.navigationService.navigateToHomePage();
    });
  }

  birthDate: FormControl<Date | null> = new FormControl<Date | null>(null);

  private isAdult(birthDate: Date): boolean {
    const birth: Date = new Date(birthDate);
    const today: Date = new Date();

    let age: number = today.getFullYear() - birth.getFullYear();
    if (18 < age) {
      return true;
    }

    if (today.getMonth() < birth.getMonth() ||
      (today.getMonth() === birth.getMonth() && today.getDate() < birth.getDate())) {
      age--;
    }

    return 18 <= age;
  }

  public handleBirthDateChange(): void {
    const value = this.birthDate.value;

    if (value && this.isAdult(value)) {
      this.nextStage();
    }
  }
}
