import {Component, Input} from "@angular/core";

@Component({
  selector: "card-tag-list",
  templateUrl: "./card-tag-list.component.html"
})
export class CardTagListComponent {

  @Input()
  public translateKeys?: string[];
}
