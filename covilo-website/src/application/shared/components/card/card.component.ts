import {Component, Input} from '@angular/core';
import {Crime} from "../../../core";

@Component({
  selector: "covilo-card",
  templateUrl: "./card.component.html"
})
export class CardComponent {

  @Input()
  public crime?: Crime;

  public getCategories(crime: Crime): string {
    if (crime.title) {
      return crime.title;
    }
    if (!crime.categories || 0 >= crime.categories.length) {
      return "";
    }
    let categories: string = "";
    for (let i: number = 0; i < crime.categories.length; i++) {
      categories += crime.categories[i].name;
      if (i < (crime.categories.length - 1)) {
        categories += ", ";
      }
    }
    return categories;
  }
}
