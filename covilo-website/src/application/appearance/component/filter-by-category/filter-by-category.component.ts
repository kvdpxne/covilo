import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Category} from "../../../core";
import {TranslateModule} from "@ngx-translate/core";
import {NgFor} from "@angular/common";

@Component({
  selector: "filter-by-category",
  templateUrl: "./filter-by-category.component.html",
  standalone: true,
  imports: [NgFor, TranslateModule]
})
export class FilterByCategoryComponent {

  @Input()
  public categories: Category[];

  @Output("changes")
  public readonly changesEmitter: EventEmitter<Category[]>;

  private readonly selectedCategories: Category[];

  public constructor() {
    this.selectedCategories = [];
    this.categories = [];
    this.changesEmitter = new EventEmitter<Category[]>();
  }

  public addOrRemoveCategory(category: Category): void {
    const index: number = this.selectedCategories.indexOf(category);
    if (-1 !== index) {
      this.selectedCategories.splice(index, 1);
    } else {
      this.selectedCategories.push(category);
    }
    this.changesEmitter.emit(this.selectedCategories);
  }
}
