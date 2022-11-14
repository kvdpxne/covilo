import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output
} from "@angular/core"
import { debounceTime, distinctUntilChanged, Subject } from "rxjs"

@Component({
  selector: "a-password-input",
  templateUrl: "./password-input.component.html",
  styleUrls: ["./password-input.component.scss"]
})
export class PasswordInputComponent implements OnInit, OnDestroy {

  @Output("password")
  passwordEvent: EventEmitter<string>

  private passwordSubject: Subject<string>
  private isPasswordVisible: boolean

  constructor() {
    this.passwordEvent = new EventEmitter<string>()
    this.passwordSubject = new Subject<string>()
    this.isPasswordVisible = false
  }

  getPasswordInputType(): string {
    return this.isPasswordVisible ? "text" : "password"
  }

  getPasswordEyeIconClass(): string {
    const eyeIcon = "bi-eye"
    return this.isPasswordVisible ? eyeIcon : `${eyeIcon}-slash`
  }

  togglePasswordVisible(): void {
    this.isPasswordVisible = !this.isPasswordVisible
  }

  onKeyUp(event: KeyboardEvent): void {
    this.passwordSubject.next(event.key)
  }

  ngOnInit(): void {
    this.passwordSubject.pipe(
      debounceTime(200),
      distinctUntilChanged()
    ).subscribe(value => {
      this.passwordEvent.emit(value)
    })
  }

  ngOnDestroy(): void {
    this.passwordSubject.unsubscribe()
  }
}
