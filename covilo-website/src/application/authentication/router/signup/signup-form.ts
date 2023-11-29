import {FormControl, FormGroup} from "@angular/forms";

export interface QualificationFormStage {
  /**
   *
   */
  birthDate: FormControl<Date>;
}

export interface BasicDataFormStage {
  /**
   *
   */
  firstName: FormControl<string>;

  /**
   *
   */
  lastName: FormControl<string>;
}

export interface AuthenticationDataFormStage {
  /**
   *
   */
  email: FormControl<string>;

  /**
   *
   */
  password: FormControl<string>;

  /**
   *
   */
  confirmPassword: FormControl<string>;

  /**
   *
   */
  privacyPolicy: FormControl<boolean>;
}

export interface SignupForm {

  qualification: FormGroup<QualificationFormStage>;

  basicData: FormGroup<BasicDataFormStage>;


  authenticationData: FormGroup<AuthenticationDataFormStage>;
}