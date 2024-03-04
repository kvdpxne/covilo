import {RouterStateSnapshot, TitleStrategy} from "@angular/router";
import {Injectable} from "@angular/core";
import {Title} from "@angular/platform-browser";

/**
 * A custom title strategy that updates the page title based on the current
 * route.
 *
 * This strategy allows for flexibility in setting the page title, either
 * based on custom logic or by default based on the route URL.
 */
@Injectable({
  providedIn: "root"
})
export class PageTitleStrategy
  extends TitleStrategy {

  /**
   * Constructs an instance of the {@link PageTitleStrategy}.
   *
   * @param title The {@link Title} service from Angular Platform Browser
   *              module used to set the page title.
   */
  public constructor(
    private readonly title: Title
  ) {
    super();
  }

  /**
   * Updates the first part of the page title with the provided part.
   *
   * This method sets the first part of the page title based on the provided
   * part, appending it before the site name. It allows for customization of
   * the page title to include additional context specific to the
   * application or current route.
   *
   * @param part The title part to be appended to the beginning of the page
   *             title. This part will be separated from the site name by a
   *             separator character. For example, if the provided part is
   *             "Dashboard", the resulting title will be "Dashboard | Covilo".
   */
  private updateTitleFirstPart(
    part: string
  ): void {
    // Set the title of the page with the provided part and the site name
    this.title.setTitle(`${part} | Covilo`);
  }

  /**
   * Overrides the {@link TitleStrategy.updateTitle} method to update the
   * page title.
   *
   * If a custom title is built using the {@link buildTitle} method, it will
   * be used; otherwise, the title will be generated based on the current
   * route URL.
   *
   * @param snapshot The current {@link RouterStateSnapshot} representing the
   *                 state of the router.
   */
  public override updateTitle(
    snapshot: RouterStateSnapshot
  ): void {
    // Build the title based on the snapshot
    const builtTitle = this.buildTitle(snapshot);

    // If a custom title is built, update the title and return
    if (builtTitle) {
      this.updateTitleFirstPart(builtTitle);
      return;
    }

    // Extract the URL from the snapshot
    const url = snapshot.url;

    // Find the last index of "/" in the URL
    const index = url.lastIndexOf("/");

    // If the index is -1, indicating that "/" is not found in the URL, return
    if (-1 == index) {
      return;
    }

    // Extract the path from the URL starting from the last "/"
    let path = url.slice(index + 1);

    // Replace underscores and hyphens with spaces
    path = path.replace(/[_-]/g, " ");

    // Capitalize the first letter of each word
    path = path.replace(/\b\w/g, (char) => char.toUpperCase());

    // Set the title of the page using the formatted path and the site name
    this.updateTitleFirstPart(path);
  }
}
