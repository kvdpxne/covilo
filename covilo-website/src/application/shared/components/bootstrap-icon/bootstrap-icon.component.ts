import {Component, Input} from "@angular/core";
import {BootstrapIcon} from "../../types/bootstrap-icon";
import {NgIf} from "@angular/common";

@Component({
  selector: "our-bootstrap-icon",
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: "./bootstrap-icon.component.html"
})
export class BootstrapIconComponent {

  /**
   *
   */
  @Input()
  public icon?: BootstrapIcon | string;

  /**
   *
   */
  public get name(): string {
    return "string" === typeof this.icon
      ? this.icon
      : this.icon?.iconName!
  }
}
