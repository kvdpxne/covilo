import { Component, Input, OnInit } from "@angular/core"
import { NgbAlertConfig } from "@ng-bootstrap/ng-bootstrap"

@Component({
  selector: "a-notification",
  templateUrl: "./notification.component.html",
  styleUrls: ["./notification.component.scss"]
})
export class NotificationComponent implements OnInit {

  @Input()
  visible?: boolean

  constructor(private readonly settings: NgbAlertConfig) {
    settings.dismissible = true
    settings.animation = true
  }

  ngOnInit(): void {
  }

  onClose() {
    this.visible = false
  }
}
