import {Component} from "@angular/core";
import {TranslateModule} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";

@Component({
  selector: "authentication-navigation-bar",
  templateUrl: "./navigation-bar.component.html",
  standalone: true,
  imports: [RouterLink, TranslateModule]
})
export class NavigationBarComponent {

}
