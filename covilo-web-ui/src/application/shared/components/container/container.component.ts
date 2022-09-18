import { Component, Input, OnInit } from "@angular/core"

@Component({
  selector: "a-container",
  templateUrl: "./container.component.html"
})
export class ContainerComponent implements OnInit {

  @Input()
  classes2?: string

  ngOnInit(): void {
  }
}
