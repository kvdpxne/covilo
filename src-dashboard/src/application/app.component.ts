import {Component, HostListener, OnDestroy, OnInit} from "@angular/core";
import {Router, RouterOutlet} from "@angular/router";
import {BrowserStorage, InMemoryStorage, Logger, StorageKey} from "./shared";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: "./app.component.html",
  styleUrl: "./app.component.scss"
})
export class AppComponent
  implements OnInit, OnDestroy {

  title = "src-dashboard";

  private readonly router: Router;

  private readonly logger: Logger;

  private readonly browserStorage: BrowserStorage;

  private readonly inMemoryStorage: InMemoryStorage;

  public constructor(
    router: Router,
    logger: Logger,
    browserStorage: BrowserStorage,
    inMemoryStorage: InMemoryStorage
  ) {
    this.router = router;

    this.logger = logger;
    this.browserStorage = browserStorage;
    this.inMemoryStorage = inMemoryStorage;
  }


  @HostListener("window:beforeunload")
  public handleWindowBeforeUnloadEvent(): void {
    this.logger.info(() =>
      "The application page window will soon be reloaded. All cached data"
      + " will be temporarily transferred to the browser's memory. Please"
      + " ensure that your application handles this gracefully to prevent data"
      + " loss or disruption for users."
    );

    //
    this.inMemoryStorage.all().forEach((value, key) => {
      this.browserStorage.store(key, value);
    });
  }

  @HostListener("window:load")
  public handleWindowLoadEvent(): void {
    // vulnerable
    const keys: StorageKey[] = [
      StorageKey.USER_TOKEN,
      StorageKey.AUTHENTICATED_USER
    ];

    keys.forEach(key => {
      this.inMemoryStorage.store(key, this.browserStorage.get(key));
      this.browserStorage.remove(key);
    });
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }
}
