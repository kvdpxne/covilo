import {Component, Input} from "@angular/core";
import {CardTagComponent} from "../card-tag/card-tag.component";
import {NgFor} from "@angular/common";

@Component({
  selector: "card-tag-list",
  templateUrl: "./card-tag-list.component.html",
  standalone: true,
  imports: [NgFor, CardTagComponent]
})
export class CardTagListComponent {

  @Input()
  public translateKeys?: string[];
}
