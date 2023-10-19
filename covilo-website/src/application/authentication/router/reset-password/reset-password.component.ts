import {Component} from "@angular/core";
import {FormControl, Validators} from "@angular/forms";
import {ResetPasswordRequest} from "../../../core";
import {NavigationService} from "../../../shared";

@Component({
  selector: "router-authentication-reset-password",
  templateUrl: "./reset-password.component.html"
})
export class ResetPasswordComponent {

  public readonly email: FormControl<string | null>;

  private readonly navigationService: NavigationService;

  public constructor(navigationService: NavigationService) {
    this.email = new FormControl<string | null>(null, {
      validators: [
        Validators.required,
        Validators.email
      ]
    });

    this.navigationService = navigationService;
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
    this.navigationService.navigateToHomePage();
  }
}
