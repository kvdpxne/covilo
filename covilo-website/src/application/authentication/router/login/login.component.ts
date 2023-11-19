import {Component} from "@angular/core";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {LoginRequest} from "../../../core";
import {NavigationService} from "../../../shared";
import {TranslateService} from "@ngx-translate/core";

interface LoginForm {

  email: FormControl<string | null>;
  password: FormControl<string | null>;
}

@Component({
  selector: "router-authentication-login",
  templateUrl: "./login.component.html"
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
  private readonly authenticationService: AuthenticationService;

  constructor(
    formBuilder: FormBuilder,
    translateService: TranslateService,
    navigationService: NavigationService,
    authenticationService: AuthenticationService
  ) {
    // Initializes the standard form group with the FormGroup constructor
    // needed to hold the user authentication information.
    this.formGroup = formBuilder.group({
      email: ["", Validators.required, Validators.email],
      password: ["", Validators.required]
    });
    this.translateService = translateService;
    //
    this.navigationService = navigationService;
    // Initiates the standard services.
    this.authenticationService = authenticationService;
  }

  public translate(key: string): string {
    return this.translateService.instant(`authentication.login.${key}`);
  }

  public submit(): void {
    //
    const email = this.formGroup.get("email")?.value;
    const password = this.formGroup.get("password")?.value;

    // Checks if the values are not null or empty.
    if (!email || !password) {
      return;
    }

    const request: LoginRequest = {
      email: email,
      password: password
    };

    this.authenticationService.login(request).subscribe((): void => {
      this.navigationService.navigateToHomePage();
    });
  }
}
