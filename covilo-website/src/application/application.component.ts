import {Component, OnInit} from "@angular/core";
import {TranslateService} from "@ngx-translate/core";
import {Icon, Link, Section} from "./shared";
import {NavigationStart, Router} from "@angular/router";
import {UserAuthenticationService} from "./core";
import {NgbDropdownConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-root",
  templateUrl: "./application.component.html"
})
export class ApplicationComponent
  implements OnInit {

  private readonly translateService: TranslateService;
  private readonly authenticationService: UserAuthenticationService;

  showFooter: boolean = true;
  showHeader: boolean = true;

  navigationItemList: Set<Link>;
  sectionSet: Set<Section>;
  iconSet: Set<Icon>;
  linkSet: Set<Link>;

  constructor(
    router: Router,
    translateService: TranslateService,
    authenticationService: UserAuthenticationService,
    ngbDropdownConfiguration: NgbDropdownConfig
  ) {
    this.translateService = translateService;
    this.authenticationService = authenticationService;

    ngbDropdownConfiguration.autoClose = "outside";

    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (event.url.includes("authentication")) {
          this.showFooter = false;
          this.showHeader = false;
        } else {
          this.showFooter = true;
          this.showHeader = true;
        }
      }
    });

    this.configureTranslateModule();

    // Initialize
    this.navigationItemList = new Set<Link>();
    this.sectionSet = new Set<Section>();
    this.iconSet = new Set<Icon>();
    this.linkSet = new Set<Link>();
  }

  /**
   *
   */
  private configureTranslateModule(): void {
    this.translateService.addLangs([
      "locale-pl",
      "locale-en"
    ]);

    let browserLanguage: string | undefined = this.translateService.getBrowserLang();
    if (browserLanguage) {
      browserLanguage = `locale-${browserLanguage}`;
      if (this.translateService.langs.includes(browserLanguage)) {
        this.translateService.use(browserLanguage);
      } else {
        this.translateService.use(this.translateService.getDefaultLang());
      }
    }
  }

  private builtInNavigationItems(): void {
    this.navigationItemList.add(new Link("core.navigation.home", "/"));
    this.navigationItemList.add(new Link("core.navigation.report", "/reporting"));
    this.navigationItemList.add(new Link("core.navigation.interactive-map", "/interactive-map"));
    this.navigationItemList.add(new Link("core.navigation.statistics", "/statistics"));
  }

  private builtInSections(): void {
    if (undefined == this.navigationItemList) {
      return;
    }
    const linkArray = new Array(this.navigationItemList.size);
    let iterator = 0;
    for (const link of this.navigationItemList) {
      linkArray[iterator] = link;
      iterator++;
    }

    const section = new Section("core.footer.navigation", linkArray);
    this.sectionSet.add(section);
    this.sectionSet.add(new Section("core.footer.company", [
      new Link("core.footer.about", ""),
      new Link("core.footer.contacts", ""),
      new Link("core.footer.news", "")
    ]));
    this.sectionSet.add(new Section("core.footer.resources", [
      new Link("core.footer.newsletters", "")
    ]));
  }

  private builtInIcons(): void {
    this.iconSet.add(new Icon("github", new Link("GitHub", "https://github.com/kvdpxne/covilo")));
    this.iconSet.add(new Icon("git", new Link("Git", "https://github.com/kvdpxne/covilo.git")));
  }

  private builtInLinks(): void {
    this.linkSet.add(new Link("Privacy & Security", ""));
    this.linkSet.add(new Link("Terms of Use", ""));
    this.linkSet.add(new Link("Trademarks", ""));
    this.linkSet.add(new Link("Legal", ""));
  }

  private initializeCurrentUser(): void {
    this.authenticationService.isLogged().subscribe({
      next: (isLogged: boolean): void => {
        if (isLogged) {
          this.authenticationService.cacheMe();
        }
      }
    });
  }

  ngOnInit(): void {
    this.initializeCurrentUser();

    this.builtInNavigationItems();
    this.builtInSections();
    this.builtInIcons();
    this.builtInLinks();
  }
}
