import {Component, Input, OnInit} from "@angular/core";
import {Icon, Link, Section} from "../../../shared";
import { TranslateModule } from "@ngx-translate/core";
import { LanguageSwitchComponent } from "../language-switch/language-switch.component";
import { NgFor } from "@angular/common";

@Component({
    selector: "app-footer",
    templateUrl: "./footer.component.html",
    styleUrls: [
        "./footer.component.scss"
    ],
    standalone: true,
    imports: [NgFor, LanguageSwitchComponent, TranslateModule]
})
export class FooterComponent implements OnInit {

  @Input() sectionSet: Set<Section> | undefined;
  @Input() iconSet: Set<Icon> | undefined;
  @Input() linkSet: Set<Link> | undefined;

  // Current year
  year: string | undefined;

  constructor() {
    const projectStartYear: number = 2022;
    const currentYear: number = new Date().getFullYear();

    if (projectStartYear >= currentYear) {
      this.year = projectStartYear.toString();
    } else {
      this.year = `${projectStartYear} - ${currentYear}`;
    }
  }

  ngOnInit(): void {
  }
}
