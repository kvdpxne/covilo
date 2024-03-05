import {Component} from "@angular/core";
import {NgbPopover} from "@ng-bootstrap/ng-bootstrap";
import { TranslateService, TranslateModule } from "@ngx-translate/core";
import { NgFor } from "@angular/common";

@Component({
    selector: "language-switch",
    templateUrl: "./language-switch.component.html",
    standalone: true,
    imports: [NgFor, NgbPopover, TranslateModule]
})
export class LanguageSwitchComponent {

  private readonly translateService: TranslateService;

  constructor(translateService: TranslateService) {
    this.translateService = translateService;
  }

  public get languages(): string[] {
    return this.translateService.langs;
  }

  public get currentLanguage(): string {
    return this.translateService.currentLang;
  }

  togglePopover(popover: NgbPopover) {
    if (popover.isOpen()) {
      popover.close();
    } else {
      popover.open();
    }
  }

  public changeLanguage(language: string): void {
    this.translateService.use(language)
  }
}
