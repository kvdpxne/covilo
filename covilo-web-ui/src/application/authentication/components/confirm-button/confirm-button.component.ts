import { Component, Input, OnInit } from "@angular/core"

@Component({
  selector: "a-confirm-button",
  templateUrl: "./confirm-button.component.html",
  styleUrls: ["./confirm-button.component.scss"]
})
export class ConfirmButtonComponent implements OnInit {

  @Input()
  displayName: string = ""

  ngOnInit(): void {
  }

}
