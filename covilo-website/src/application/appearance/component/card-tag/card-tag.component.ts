import {Component, Input} from "@angular/core";

@Component({
  selector: "card-tag",
  templateUrl: "./card-tag.component.html",
})
export class CardTagComponent {

  /**
   * Name of the translation key after which the name will be translated.
   */
  @Input()
  public translateKey: string;

  constructor() {
    this.translateKey = "";
  }
}
