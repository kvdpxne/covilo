import {Component} from "@angular/core";
import {FormBuilder, FormGroup, FormsModule, NonNullableFormBuilder, Validators} from "@angular/forms";
import {LoginRequest, UserAuthenticationService} from "../../../core";
import {NavigationService} from "../../../shared";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {LoginForm} from "./login-form";
import {RouterLink} from "@angular/router";
import {TextFieldComponent} from "../../components/text-field/text-field.component";

@Component({
  selector: "router-authentication-login",
  templateUrl: "./login.component.html",
  standalone: true,
  imports: [FormsModule, TextFieldComponent, RouterLink, TranslateModule]
})
export class LoginComponent {

  /**
   * A group of control forms needed to hold user authentication information.
   */
  public readonly formGroup: FormGroup<LoginForm>;

  private readonly translateService: TranslateService;
  private readonly navigationService: NavigationService;

  /**
   *
   */
  private readonly authenticationService: UserAuthenticationService;

  constructor(
    formBuilder: FormBuilder,
    translateService: TranslateService,
    navigationService: NavigationService,
    authenticationService: UserAuthenticationService
  ) {
    //
    const builder: NonNullableFormBuilder = formBuilder.nonNullable;

    // Initializes the standard form group with the FormGroup constructor
    // needed to hold the user authentication information.
    this.formGroup = builder.group<LoginForm>({
      email: builder.control<string>("", [
          Validators.required,
          Validators.email
        ]
      ),
      password: builder.control<string>("", Validators.required),
      rememberMe: builder.control<boolean>(false)
    });

    // Initializes dependency services.
    this.translateService = translateService;

    // Initializes our services.
    this.navigationService = navigationService;
    this.authenticationService = authenticationService;
  }

  public translate(key: string): string {
    return this.translateService.instant(`authentication.login.${key}`);
  }

  public submit(): void {
    // Variables needed for user authentication.
    const email: string = this.formGroup.controls["email"].value;
    const password: string = this.formGroup.controls["password"].value;

    // Checks if the values are not null or empty.
    if (!email || !password) {
      return;
    }

    //
    const rememberMe: boolean = this.formGroup.controls["rememberMe"].value;

    const request: LoginRequest = {
      email: email,
      password: password,
      rememberMe: rememberMe
    };

    this.authenticationService.login(request).subscribe((): void => {
      this.navigationService.navigateToHomePage();
    });
  }
}
