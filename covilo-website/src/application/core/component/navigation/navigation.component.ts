import {Component, Input} from "@angular/core";
import {Link, StorageKey, StorageService} from "../../../shared";
import { TranslateModule } from "@ngx-translate/core";
import { AvatarComponent } from "../avatar/avatar.component";
import { RouterLinkActive, RouterLink } from "@angular/router";
import { NgFor, NgIf } from "@angular/common";
import { NgbCollapse } from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: "a-navigation",
    templateUrl: "./navigation.component.html",
    standalone: true,
    imports: [
        NgbCollapse,
        NgFor,
        RouterLinkActive,
        RouterLink,
        NgIf,
        AvatarComponent,
        TranslateModule,
    ],
})
export class NavigationComponent {

  // If true, will collapse the element or show it otherwise
  isCollapsed: boolean = true;

  private readonly storageService: StorageService;

  isLoggedIn: boolean;

  @Input()
  itemList!: Set<Link>;

  constructor(storageService: StorageService) {
    this.storageService = storageService;
    this.isLoggedIn = this.storageService.has(StorageKey.TOKEN);
  }

  toggleCollapse(): void {
    this.isCollapsed = !this.isCollapsed;
  };
}
