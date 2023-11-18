import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {NavigationService, StorageKey, StorageService} from "../../../shared";
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

  private readonly signupForm: FormGroup;

  private readonly translateService: TranslateService;
  private readonly navigationService: NavigationService;
  private readonly storageService: StorageService;
  private readonly authenticationService: AuthenticationService;

  public readonly stage: typeof SignupStage;
  public signupFormData: SignupFormData;

  /**
   * Current stage of user registration.
   */
  public currentStage: SignupStage;

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
        birthDate: ""
      }),
      basicData: formBuilder.group({
        firstName: ["", Validators.required],
        lastName: ["", Validators.required]
      }),
      authenticationData: formBuilder.group({
        email: ["", Validators.email],
        password: ["", Validators.required],
        confirmPassword: ["", Validators.required],
        privacyPolicy: [false, Validators.requiredTrue]
      })
    });

    // Initializes dependency services.
    this.translateService = translateService;

    // Initializes our services.
    this.navigationService = navigationService;
    this.storageService = storageService;
    this.authenticationService = authenticationService;

    // Initializes constants.
    this.stage = SignupStage;

    // Initializes variables.
    this.signupFormData = new SignupFormData();
    this.currentStage = SignupStage.QUALIFICATION;
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
    this.storageService.store<SignupFormData>(
      StorageKey.SIGNUP_DATA, this.signupFormData
    );
  }

  public submit(): void {
    //
    const birthDate: Date = this.signupFormData.birthDate!;
    const firstName: string = this.signupFormData.firstName!;
    const lastName: string = this.signupFormData.lastName!;
    const email: string = this.signupFormData.email!;
    const privacyPolicy: boolean = this.signupFormData.privacyPolicy!;

    //
    const password: string = this.value("password");
    const confirmPassword: string = this.value("confirmPassword");

    // Checks if the values are not null or empty.
    if (!birthDate || !firstName || !lastName || !email || !password || !confirmPassword || !privacyPolicy) {
      return;
    }

    //
    const request: SignupRequest = {
      birthDate: birthDate,
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      confirmPassword: confirmPassword,
      privacyPolicy: privacyPolicy
    };

    //
    this.authenticationService.signup(request).subscribe((): void => {
      this.storageService.delete(StorageKey.SIGNUP_DATA);
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
      if (firstName) {
        this.signupFormData.firstName = firstName;
        this.updateSignupData();
      }
      if (lastName) {
        this.signupFormData.lastName = lastName;
        this.updateSignupData();
      }
      if (firstName && lastName) {
        this.nextStage(SignupStage.AUTHENTICATION_DATA);
      }
    });
  }

  public handleAuthenticationDataChange(): void {
    this.authenticationData.valueChanges.subscribe(value => {
      const email: string = value.email;
      const privacyPolicy: boolean = value.privacyPolicy;
      if (email) {
        this.signupFormData.email = email;
        this.updateSignupData();
      }
      if (privacyPolicy) {
        this.signupFormData.privacyPolicy = privacyPolicy;
        this.updateSignupData();
      }
      if (email && privacyPolicy) {
        this.nextStage(SignupStage.INTIMATION);
      }
    });
  }

  public ngOnInit(): void {
    // Tries to load stored user registration data.
    const data: SignupFormData | null = this.storageService
      .load<SignupFormData>(StorageKey.SIGNUP_DATA);

    if (data) {
      this.signupFormData = data;
      console.debug("Stored registration data has been loaded.");

      const birthDate = this.signupFormData.birthDate;
      if (birthDate) {
        this.qualification.get("birthDate")?.setValue(birthDate);
        this.nextStage(SignupStage.BASIC_DATA);
      }

      const firstName = this.signupFormData.firstName;
      if (firstName) {
        this.basicData.get("firstName")?.setValue(firstName);
      }

      const lastName = this.signupFormData.lastName;
      if (lastName) {
        this.basicData.get("lastName")?.setValue(lastName);
      }

      if (firstName && lastName) {
        this.nextStage(SignupStage.AUTHENTICATION_DATA);
      }

      const email = this.signupFormData.email;
      if (email) {
        this.authenticationData.get("email")?.setValue(email);
      }

      const privacyPolicy = this.signupFormData.privacyPolicy;
      if (privacyPolicy) {
        this.authenticationData.get("privacyPolicy")?.setValue(privacyPolicy);
      }
    }

    this.handleQualificationChange();
    this.handleBasicDataChange();
    this.handleAuthenticationDataChange();
  }
}
