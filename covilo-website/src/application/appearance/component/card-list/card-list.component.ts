import {Component, Input} from '@angular/core';
import {Crime} from "../../../core";

@Component({
  selector: "card-list",
  templateUrl: "./card-list.component.html"
})
export class CardListComponent {

  @Input()
  public crimes!: Crime[]

  @Input()
  public start!: number;

  @Input()
  public end!: number;
}
