import {FormControl} from "@angular/forms";

export interface ChangeEmailForm {

  currentPassword: FormControl<string>;

  newEmail: FormControl<string>;

  confirmedEmail: FormControl<string>;
}