import {Component} from "@angular/core";
import {NgbPopover} from "@ng-bootstrap/ng-bootstrap";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: "language-switch",
  templateUrl: "./language-switch.component.html"
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
