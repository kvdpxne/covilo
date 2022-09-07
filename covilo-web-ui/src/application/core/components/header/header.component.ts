import { Component, Input, OnInit } from '@angular/core';
import { Link } from "../../../shared/types/link";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  // If true, will collapse the element or show it otherwise
  isCollapsed: boolean = true;

  @Input()
  itemList!: Set<Link>

  toggleCollapse(): void {
    this.isCollapsed = !this.isCollapsed;
  };

  ngOnInit(): void {
  }
}
