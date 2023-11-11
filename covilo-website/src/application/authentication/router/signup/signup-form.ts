import {FormControl, FormGroup} from "@angular/forms";

export interface SignupForm {

  birthDate: FormControl<Date | null>;

  firstName: FormControl<string | null>;

  lastName: FormControl<string | null>;

  email: FormControl<string | null>;

  password: FormControl<string | null>;

  confirmPassword: FormControl<string | null>;

  privacyPolicy: FormControl<boolean | null>;
}

export type SignupFormGroup = FormGroup<SignupForm>;