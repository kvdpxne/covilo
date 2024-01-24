import {Component, Input} from "@angular/core";
import {SideNavigationItem} from "../../model/side-navigation-item";
import {BootstrapIconComponent} from "../../../shared";
import {RouterLink} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";
import {NgIf} from "@angular/common";

@Component({
  selector: "our-side-navigation-item",
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    TranslateModule,
    BootstrapIconComponent
  ],
  templateUrl: "./side-navigation-item.component.html"
})
export class SideNavigationItemComponent {

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
