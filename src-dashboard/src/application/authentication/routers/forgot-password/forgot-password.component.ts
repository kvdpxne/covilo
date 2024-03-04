import {Component, OnInit} from "@angular/core";
import {
  FormBuilder, FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule, Validators
} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    // Angular
    FormsModule,
    ReactiveFormsModule,
    RouterLink,

    // Angular Material
    MatButton,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle,
    MatCheckbox,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
  ],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.scss'
})
export class ForgotPasswordComponent
  implements OnInit {

  /**
   *
   */
  private readonly formBuilder: FormBuilder;

  /**
   *
   */
  private _formGroup?: FormGroup;

  public constructor(formBuilder: FormBuilder) {
    this.formBuilder = formBuilder;
  }

  private createFormGroup(): void {
    const builder = this.formBuilder.nonNullable;

    this._formGroup = builder.group({
      username: ["", Validators.required]
    });
  }

  public ngOnInit(): void {
    this.createFormGroup();
  }

  public get formGroup(): FormGroup {
    if (!this._formGroup) {
      this.createFormGroup();
    }

    return this._formGroup!
  }

  public get usernameControl(): FormControl {
    return <FormControl<any>>this.formGroup?.controls["username"]!
  }

  public onSubmit(): void {

  }
}
