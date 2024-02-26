import {Component, Input} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: "text-field",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./text-field.component.html"
})
export class TextFieldComponent {

  @Input()
  public name!: string;

  @Input()
  public text?: string;

  @Input()
  public feedback?: string;

  @Input()
  public group!: FormGroup;

  @Input()
  public type?: string;

  @Input()
  public autocomplete?: boolean;

  @Input()
  public placeholder?: string;

  @Input()
  public sent: boolean = false;

  public get control(): FormControl<any> {
    return this.group.get(this.name) as FormControl<any>;
  }

  public get checkboxType(): boolean {
    return "checkbox" === this.type;
  }

  public get dataType(): boolean {
    return "data" === this.type;
  }

  public get valid(): boolean {
    return this.control.invalid && (this.control.dirty || this.control.touched);
  }

  public get errors(): boolean {
    return null != this.control.errors;
  }
}
