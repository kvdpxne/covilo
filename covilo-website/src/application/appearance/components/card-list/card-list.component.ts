import {Component, Input} from "@angular/core";
import {Crime} from "../../../core";
import {CardComponent} from "../card/card.component";
import {NgFor, SlicePipe} from "@angular/common";

@Component({
  selector: "card-list",
  templateUrl: "./card-list.component.html",
  standalone: true,
  imports: [NgFor, CardComponent, SlicePipe]
})
export class CardListComponent {

  @Input()
  public crimes!: Crime[];

  @Input()
  public start!: number;

  @Input()
  public end!: number;
}
