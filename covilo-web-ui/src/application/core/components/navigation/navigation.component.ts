import { Component, Input, OnInit } from "@angular/core"
import { Link } from "../../../shared/types/link"

@Component({
  selector: "a-navigation",
  templateUrl: "./navigation.component.html",
  styleUrls: [
    "./navigation.component.scss"
  ]
})
export class NavigationComponent implements OnInit {

  // If true, will collapse the element or show it otherwise
  isCollapsed: boolean = true

  @Input()
  itemList!: Set<Link>

  toggleCollapse(): void {
    this.isCollapsed = !this.isCollapsed
  };

  ngOnInit(): void {
  }
}
