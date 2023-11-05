import {Component, Input} from "@angular/core";

@Component({
  selector: "side-navigation-bar",
  templateUrl: "./side-navigation-bar.component.html"
})
export class SideNavigationBarComponent {

  @Input()
  public fullName?: string;

  @Input()
  public email?: string;
}
