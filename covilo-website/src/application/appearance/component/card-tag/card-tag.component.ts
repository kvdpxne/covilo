import {Component, Input} from "@angular/core";
import {TranslateModule} from "@ngx-translate/core";

@Component({
  selector: "card-tag",
  templateUrl: "./card-tag.component.html",
  standalone: true,
  imports: [TranslateModule]
})
export class CardTagComponent {

  /**
   * Name of the translation key after which the name will be translated.
   */
  @Input()
  public translatableNameKey: string;

  constructor() {
    this.translatableNameKey = "";
  }
}
