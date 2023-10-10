import { Component, OnInit } from "@angular/core"
import { TranslateService } from "@ngx-translate/core"
import { Section } from "./shared/types/section"
import { Icon } from "./shared/types/icon"
import { Link } from "./shared/types/link"
import { NavigationStart, Router } from "@angular/router"

//
@Component({
  selector: "app-root",
  templateUrl: "./application.component.html",
  styleUrls: [
    "./application.component.scss"
  ]
})
export class ApplicationComponent implements OnInit {

  showFooter: boolean = true
  showHeader: boolean = true

  navigationItemList: Set<Link>
  sectionSet: Set<Section>
  iconSet: Set<Icon>
  linkSet: Set<Link>

  constructor(
    router: Router,
    private readonly translate: TranslateService
  ) {
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (event.url.includes("authentication")) {
          this.showFooter = false
          this.showHeader = false
        } else {
          this.showFooter = true
          this.showHeader = true
        }
      }
    })

    this.translate.addLangs([
      "locale-pl",
      "locale-en"
    ])
    this.translate.setDefaultLang("locale-en")

    let browserLanguage: string | undefined = this.translate.getBrowserLang()
    if (browserLanguage) {
      browserLanguage = `locale-${browserLanguage}`
      if (this.translate.langs.includes(browserLanguage)) {
        this.translate.use(browserLanguage)
      } else {
        this.translate.use(this.translate.getDefaultLang())
      }
    }

    // Initialize
    this.navigationItemList = new Set<Link>()
    this.sectionSet = new Set<Section>()
    this.iconSet = new Set<Icon>()
    this.linkSet = new Set<Link>()
  }

  private builtInNavigationItems(): void {
    this.navigationItemList.add(new Link("core.navigation.home", "/"))
    this.navigationItemList.add(new Link("core.navigation.report", "/reporting"))
    this.navigationItemList.add(new Link("core.navigation.statistics", "/statistics"))
  }

  private builtInSections(): void {
    if (undefined == this.navigationItemList) {
      return
    }
    const linkArray = new Array(this.navigationItemList.size)
    let iterator = 0
    for (const link of this.navigationItemList) {
      linkArray[iterator] = link
      iterator++
    }

    const section = new Section("core.footer.navigation", linkArray)
    this.sectionSet.add(section)
    this.sectionSet.add(new Section("core.footer.company", [
      new Link("core.footer.about", ""),
      new Link("core.footer.contacts", ""),
      new Link("core.footer.news", "")
    ]))
    this.sectionSet.add(new Section("core.footer.resources", [
      new Link("core.footer.newsletters", "")
    ]))
  }

  private builtInIcons(): void {
    this.iconSet.add(new Icon("github", new Link("GitHub", "https://github.com/kvdpxne/covilo")))
    this.iconSet.add(new Icon("git", new Link("Git", "https://github.com/kvdpxne/covilo.git")))
  }

  private builtInLinks(): void {
    this.linkSet.add(new Link("Privacy & Security", ""))
    this.linkSet.add(new Link("Terms of Use", ""))
    this.linkSet.add(new Link("Trademarks", ""))
    this.linkSet.add(new Link("Legal", ""))
  }

  ngOnInit(): void {
    this.builtInNavigationItems()
    this.builtInSections()
    this.builtInIcons()
    this.builtInLinks()
  }
}
