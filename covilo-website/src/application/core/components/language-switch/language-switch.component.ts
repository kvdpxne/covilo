import {Component} from "@angular/core";
import {NgbPopover} from "@ng-bootstrap/ng-bootstrap";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: "language-switch",
  templateUrl: "./language-switch.component.html"
})
export class LanguageSwitchComponent {

  private readonly translateService: TranslateService;

  public currentLanguage: string;

  constructor(translateService: TranslateService) {
    this.translateService = translateService;
    this.currentLanguage = "English"
  }

  name = 'World';

  public get languages(): string[] {
    return this.translateService.langs;
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
