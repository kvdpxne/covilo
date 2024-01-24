import {Component, EventEmitter, Input, Output} from "@angular/core";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Nameable} from "../../../core/aggregation/nameable";
import {TextFilterPipe} from "../../pipe/text-filter.pipe";
import {NgFor, NgIf} from "@angular/common";
import {NgbDropdown, NgbDropdownItem, NgbDropdownMenu, NgbDropdownToggle} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "input-multiple-select",
  templateUrl: "./input-multiple-select.component.html",
  standalone: true,
  imports: [NgbDropdown, NgbDropdownToggle, NgbDropdownMenu, NgIf, FormsModule, ReactiveFormsModule, NgFor, NgbDropdownItem, TranslateModule, TextFilterPipe]
})
export class InputMultipleSelectComponent<T extends Nameable> {

  /**
   * Unique name for the component.
   */
  @Input()
  public name: string;

  /**
   * The title of the component that will be visible in the selection input.
   */
  @Input()
  public title: string;

  /**
   * Specifies whether the input for option search should be rendered.
   */
  @Input()
  public showSearcher: boolean;

  /**
   * Specifies whether the all-options select button should be rendered.
   */
  @Input()
  public showSelectAll: boolean;

  /**
   *
   */
  @Input()
  public options: T[];

  @Output("selectAll")
  public readonly selectedOptionsEmitter: EventEmitter<T[]>;

  private readonly translateService: TranslateService;
  public readonly searcher: FormControl<string | null>;
  public selectedOptions: T[];

  public constructor(translateService: TranslateService) {
    this.translateService = translateService;
    this.searcher = new FormControl<string>("");
    this.selectedOptions = [];
    // Default values for input fields.
    this.name = "";
    this.title = "";
    this.showSearcher = true;
    this.showSelectAll = true;
    this.options = [];
    // Output relays.
    this.selectedOptionsEmitter = new EventEmitter<T[]>();
  }

  public translateOption(option: T): string {
    if (option.translatableNameKey) {
      return this.translateService.instant(option.translatableNameKey);
    }
    throw Error("The key to translating the name is undefined.");
  }

  public get translatedSelectedOptions(): string {
    if (0 !== this.selectedOptions.length) {
      return this.selectedOptions.map<string>((selectedOption: T): string =>
        this.translateOption(selectedOption)
      ).join(", ");
    }
    return this.translateService.instant(this.title);
  }

  private emitAllOptions(): void {
    this.selectedOptionsEmitter.emit(this.selectedOptions);
  }

  /**
   * Adds or removes options from the selected options array. If the passed
   * option already exists in the selected options array, it will be removed;
   * otherwise it will be added.
   */
  public addOrRemoveOption(option: T): void {
    const index: number = this.selectedOptions.indexOf(option);
    if (-1 !== index) {
      this.selectedOptions.splice(index, 1);
    } else {
      this.selectedOptions.push(option);
    }
    this.emitAllOptions();
  }

  /**
   * Adds or removes all options from the selected options array. If the array
   * of selected options is the same size as the array of options passed to
   * this component, all selected options will be removed, otherwise all
   * options will be added to the array of selected options.
   */
  public addOrRemoveAllOptions(): void {
    if (this.selectedOptions.length === this.options.length) {
      this.selectedOptions = [];
    } else {
      this.selectedOptions = this.options;
    }
    this.emitAllOptions();
  }

  /**
   * Checks whether the passed option exists in the array of selected options.
   */
  public containsOption(option: T): boolean {
    return -1 !== this.selectedOptions.indexOf(option);
  }
}
