import {Component, Input} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: "text-field",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  templateUrl: "./text-field.component.html"
})
export class TextFieldComponent {

  @Input()
  public name!: string

  @Input()
  public text!: string

  @Input()
  public invalidText!: string

  @Input()
  public group!: FormGroup;

  @Input()
  public type: string = "text";

  @Input()
  public autocomplete: boolean = false;

  @Input()
  placeholder!: string;

  @Input()
  public sent: boolean = false;

  public get control(): FormControl {
    return this.group.get(this.name) as FormControl;
  }

  public isValid(): boolean {
    return this.control.invalid && (this.control.dirty || this.control.touched);
  }

  public hasErrors(): boolean {
    return null != this.control.errors;
  }
}
