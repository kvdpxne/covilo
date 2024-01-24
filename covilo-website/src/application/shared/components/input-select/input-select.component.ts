import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild
} from "@angular/core"
import { TranslateService, TranslateModule } from "@ngx-translate/core";
import { TextFilterPipe } from "../../pipe/text-filter.pipe";
import { NgFor } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { NgbDropdown, NgbDropdownToggle, NgbDropdownMenu, NgbDropdownItem } from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: "input-select",
    templateUrl: "./input-select.component.html",
    styleUrls: [
        "./input-select.component.scss"
    ],
    standalone: true,
    imports: [NgbDropdown, FormsModule, NgbDropdownToggle, NgbDropdownMenu, NgFor, NgbDropdownItem, TranslateModule, TextFilterPipe]
})
export class InputSelectComponent implements AfterViewInit, OnInit {

  private readonly translateService: TranslateService

  // current value in the input selector
  value: string = ""
  @Input() name!: string
  placeholder!: string
  @Input() suggestions?: any[] = []
  @Input() disabled!: boolean
  @Output() key: EventEmitter<any> = new EventEmitter<any>()
  @ViewChild("input")
  private readonly input?: ElementRef<HTMLInputElement>

  constructor(translateService: TranslateService) {
    this.translateService = translateService
  }

  getSuggestionReadableName(entity: any): string {
    if ("nationalName" in entity) {
      return entity.nationalName
    }
    // Only the Country entity has no domestic name
    return `country.${entity.name}`
  }

  setKey(entity: any): void {
    this.key.emit(entity)
    if ("nationalName" in entity) {
      this.set(entity.nationalName)
    } else {
      let translated: string = "";
      this.translateService.get(`country.${entity.name}`).subscribe({
        next: (value) => translated = value
      });

      this.set(translated)
    }
  }

  ngOnInit(): void {
    if (!this.placeholder) {
      this.placeholder = `shared.common.${this.name}`
    }
  }

  ngAfterViewInit(): void {
  }

  /**
   *
   */
  private getInput(): HTMLInputElement {
    if (!this.input) {
      throw new Error("Something went wrong.")
    }
    return this.input.nativeElement
  }

  /**
   *
   */
  private getInputValue(): string {
    const currentValue = this.getInput().value
    this.value = currentValue
    return currentValue
  }

  private set(value: string): void {
    this.getInput().value = value
    this.getInputValue()
  }
}
