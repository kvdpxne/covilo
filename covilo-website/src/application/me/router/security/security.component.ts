import {Component} from "@angular/core";
import {UserMeChangePasswordRequest, UserService} from "../../../core";
import {FormBuilder, FormControl, FormGroup, FormsModule, NonNullableFormBuilder, Validators} from "@angular/forms";
import {ChangePasswordForm} from "../../fsf/change-password-form";
import {TranslateModule} from "@ngx-translate/core";
import {TextFieldComponent} from "../../../authentication/component/text-field/text-field.component";

@Component({
  selector: "route-change-password",
  templateUrl: "./security.component.html",
  standalone: true,
  imports: [
    FormsModule,
    TextFieldComponent,
    TranslateModule
  ]
})
export class SecurityComponent {

  public readonly changePasswordForm: FormGroup<ChangePasswordForm>;

  private readonly userService: UserService;

  public constructor(
    formBuilder: FormBuilder,
    userService: UserService
  ) {
    //
    const builder: NonNullableFormBuilder = formBuilder.nonNullable;

    this.changePasswordForm = builder.group<ChangePasswordForm>({
      currentPassword: builder.control<string>("", Validators.required),
      newPassword: builder.control<string>("", [
        Validators.required,
        Validators.minLength(8)
      ]),
      confirmedPassword: builder.control<string>("", [
        Validators.required,
        Validators.minLength(8)
      ])
    });

    // Initializes our services.
    this.userService = userService;
  }

  private get currentPassword(): FormControl<string> {
    return this.changePasswordForm.controls["currentPassword"];
  }

  private get newPassword(): FormControl<string> {
    return this.changePasswordForm.controls["newPassword"];
  }

  private get confirmedPassword(): FormControl<string> {
    return this.changePasswordForm.controls["confirmedPassword"];
  }

  public onSubmit(): void {
    const currentPassword: string = this.currentPassword.value;
    const newPassword: string = this.newPassword.value;
    const confirmedPassword: string = this.confirmedPassword.value;

    if (!currentPassword || !newPassword || !confirmedPassword) {
      return;
    }

    const request: UserMeChangePasswordRequest = {
      currentPassword: currentPassword,
      newPassword: newPassword,
      confirmedPassword: confirmedPassword
    };

    this.userService.updatePassword(request).subscribe();
  }
}
