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
      // const translated = this.translate.transform(`country.${entity.key}`)
      this.set(entity.name)
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
