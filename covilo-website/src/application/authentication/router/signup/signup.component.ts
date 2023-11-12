import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {NavigationService, StorageService} from "../../../shared";
import {SignupStage} from "./signup-stage";
import {TranslateService} from "@ngx-translate/core";
import {SignupFormData} from "./signup-form-data";
import {SignupRequest} from "../../../core";

@Component({
  selector: "router-authentication-signup",
  templateUrl: "./signup.component.html"
})
export class SignupComponent
  implements OnInit {

  private readonly translateService: TranslateService;
  private readonly navigationService: NavigationService;
  private readonly storageService: StorageService;
  private readonly authenticationService: AuthenticationService;

  /**
   *
   */
  public readonly stage: typeof SignupStage;

  /**
   * Current stage of user registration.
   */
  public currentStage: SignupStage;

  public signupForm: FormGroup;

  public signupFormData: SignupFormData;

  public constructor(
    formBuilder: FormBuilder,
    translateService: TranslateService,
    navigationService: NavigationService,
    storageService: StorageService,
    authenticationService: AuthenticationService
  ) {
    // Initializes the standard form group with the FormGroup constructor
    // needed to hold the user authentication information.
    this.signupForm = formBuilder.group({
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

    this.translateService = translateService;

    //
    this.navigationService = navigationService;
    this.storageService = storageService;
    this.authenticationService = authenticationService;

    this.stage = SignupStage;

    //
    this.currentStage = SignupStage.QUALIFICATION;
    this.signupFormData = new SignupFormData();
  }

  public translate(key: string): string {
    return this.translateService.instant(`authentication.signup.${key}`);
  }

  public get qualification(): FormGroup {
    return this.signupForm.get("qualification") as FormGroup;
  }

  public get basicData(): FormGroup {
    return this.signupForm.get("basicData") as FormGroup;
  }

  public get authenticationData(): FormGroup {
    return this.signupForm.get("authenticationData") as FormGroup;
  }

  private value(key: string): string {
    return this.authenticationData.get(key)?.value;
  }

  /**
   *
   */
  public nextStage(stage: SignupStage): void {
    const nextStage: number = 1 + this.currentStage;
    this.currentStage = nextStage > stage
      ? stage
      : nextStage;
  }

  /**
   *
   */
  public showStage(stage: SignupStage): boolean {
    return this.currentStage >= stage;
  }

  public updateSignupData(): void {
    this.storageService.store<SignupFormData>("signup_data", this.signupFormData);
  }

  public submit(): void {
    //
    const birthDate: Date = this.signupFormData.birthDate!;
    const firstName: string = this.signupFormData.firstName!;
    const lastName: string = this.signupFormData.lastName!;
    const email: string = this.signupFormData.email!;

    //
    const password: string = this.value("password");
    const confirmPassword: string = this.value("confirmPassword");

    //
    const request: SignupRequest = {
      birthDate: birthDate,
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      confirmPassword: confirmPassword,
      privacyPolicy: true
    };

    //
    this.authenticationService.signup(request).subscribe((): void => {
      this.navigationService.navigateToHomePage();
    });
  }

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

  public handleQualificationChange(): void {
    this.qualification.valueChanges.subscribe(value => {
      if (this.isAdult(value.birthDate)) {
        this.signupFormData.birthDate = value.birthDate;
        this.updateSignupData();
        this.nextStage(SignupStage.BASIC_DATA);
      }
    });
  }

  public handleBasicDataChange(): void {
    this.basicData.valueChanges.subscribe(value => {
      const firstName: string = value.firstName;
      const lastName: string = value.lastName;
      if (firstName && lastName) {
        this.signupFormData.firstName = firstName;
        this.signupFormData.lastName = lastName;
        this.updateSignupData();
        this.nextStage(SignupStage.AUTHENTICATION_DATA);
      }
    });
  }

  public handleAuthenticationDataChange(): void {
    this.authenticationData.valueChanges.subscribe(value => {
      const email: string = value.email;
      if (email && value.password && value.confirmPassword) {
        this.signupFormData.email = email;
        this.updateSignupData();
        this.nextStage(SignupStage.INTIMATION);
      }
    });
  }

  public ngOnInit(): void {
    this.handleQualificationChange();
    this.handleBasicDataChange();
    this.handleAuthenticationDataChange();
  }
}
