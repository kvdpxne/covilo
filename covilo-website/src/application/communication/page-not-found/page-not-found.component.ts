import {Component} from "@angular/core";
import {TranslateModule} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";

@Component({
  selector: "router-page-not-found",
  standalone: true,
  imports: [
    RouterLink,
    TranslateModule
  ],
  templateUrl: "./page-not-found.component.html"
})
export class PageNotFoundComponent { }
