import { Component, OnInit } from "@angular/core"
import { TranslateService } from "@ngx-translate/core"
import { Section } from "./shared/types/section"
import { Icon } from "./shared/types/icon"
import { Link } from "./shared/types/link"
import { NavigationStart, Router } from "@angular/router"
import { User } from "./core"

//
@Component({
  selector: "app-root",
  templateUrl: "./application.component.html",
  styleUrls: [
    "./application.component.scss"
  ]
})
export class ApplicationComponent implements OnInit {

  currentUser?: User

  showFooter: boolean = true
  showHeader: boolean = true

  navigationItemList: Set<Link>
  sectionSet: Set<Section>
  iconSet: Set<Icon>
  linkSet: Set<Link>

  constructor(
    private readonly router: Router,
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
    this.translate.use("locale-en")

    // Initialize
    this.navigationItemList = new Set<Link>()
    this.sectionSet = new Set<Section>()
    this.iconSet = new Set<Icon>()
    this.linkSet = new Set<Link>()
  }

  private builtInNavigationItems(): void {
    this.navigationItemList.add(new Link("navigation.home", "/"))
    this.navigationItemList.add(new Link("navigation.statistics", "/statistics"))
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
    const section = new Section("Navigation", linkArray)
    this.sectionSet.add(section)
    this.sectionSet.add(new Section("Company", [
      new Link("About", ""),
      new Link("Contacts", ""),
      new Link("News", "")
    ]))
    this.sectionSet.add(new Section("Resources", [
      new Link("Newsletters", "")
    ]))
  }

  private builtInIcons(): void {
    this.iconSet.add(new Icon("github", new Link("GitHub", "")))
    this.iconSet.add(new Icon("git", new Link("Git", "")))
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
