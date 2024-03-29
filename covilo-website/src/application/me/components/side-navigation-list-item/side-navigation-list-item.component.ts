import {Component, Input} from "@angular/core";
import {SideNavigationItem} from "../../models/side-navigation-item";
import {BootstrapIconComponent} from "../../../shared";
import {RouterLink} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";
import {NgIf} from "@angular/common";

@Component({
  selector: "our-me-side-navigation-list-item",
  templateUrl: "./side-navigation-list-item.component.html",
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    TranslateModule,
    BootstrapIconComponent
  ]
})
export class SideNavigationListItemComponent {

  @Input()
  public item?: SideNavigationItem;

  public get translatableNameKey(): string {
    return `me.side-navigation.${this.item?.translatableNameKey}`;
  }

  /**
   * Specifies whether the icon should be rendered.
   */
  public get shouldRenderIcon(): boolean {
    return !!this.item?.iconName;
  }
}
