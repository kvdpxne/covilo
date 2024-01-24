import {Component, Input, OnInit} from "@angular/core";
import { NgbAlertConfig, NgbAlert } from "@ng-bootstrap/ng-bootstrap";
import { NgIf } from "@angular/common";

@Component({
    selector: "a-notification",
    templateUrl: "./notification.component.html",
    styleUrls: ["./notification.component.scss"],
    standalone: true,
    imports: [NgIf, NgbAlert]
})
export class NotificationComponent implements OnInit {

  @Input()
  visible?: boolean;

  constructor(private readonly settings: NgbAlertConfig) {
    settings.dismissible = true;
    settings.animation = true;
  }

  ngOnInit(): void {
  }

  onClose() {
    this.visible = false;
  }
}
