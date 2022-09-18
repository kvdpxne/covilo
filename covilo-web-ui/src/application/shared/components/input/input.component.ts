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

@Component({
  selector: "a-input",
  templateUrl: "./input.component.html",
  styleUrls: [
    "./input.component.scss"
  ]
})
export class InputComponent implements AfterViewInit, OnInit {

  // current value in the input selector
  value: string = ""
  @Input() name!: string
  placeholder!: string
  @Input() suggestions?: any[] = []
  @Input() disabled!: boolean
  @Output() key: EventEmitter<any> = new EventEmitter<any>()
  @ViewChild("input")
  private readonly input?: ElementRef<HTMLInputElement>

  getSuggestionReadableName(entity: any): string {
    if ("domesticName" in entity) {
      return entity.domesticName
    }
    // Only the LocationCountry entity has no domestic name
    return `country.${entity.key}`
  }

  setKey(entity: any): void {
    this.key.emit(entity)
    if ("domesticName" in entity) {
      this.set(entity.domesticName)
    } else {
      // const translated = this.translate.transform(`country.${entity.key}`)
      this.set(entity.key)
    }
  }

  ngOnInit(): void {
    if (!this.placeholder) {
      this.placeholder = `word.${this.name}`
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
