import {Component} from "@angular/core";
import {FormControl, Validators} from "@angular/forms";
import {ResetPasswordRequest} from "../../payload/reset-password-request";

@Component({
  selector: "covilo-authentication-forgot-password",
  templateUrl: "./forgot-password.component.html"
})
export class ForgotPasswordComponent {

  public readonly email: FormControl<string | null>;

  constructor() {
    this.email = new FormControl<string | null>(null, {
      validators: [
        Validators.required,
        Validators.email
      ]
    });
  }

  public isEmailValid(): boolean {
    return this.email.invalid && (this.email.dirty || this.email.touched);
  }

  public submit(): void {
    const email: string | null = this.email.value;

    if (!email) {
      return;
    }

    const resetPasswordRequest: ResetPasswordRequest = {
      email: email
    };

    // TODO reset password request
  }
}
