import {Component, OnInit} from "@angular/core";
import {
  ActivatedRoute,
  NavigationEnd,
  Router,
  RouterLink
} from "@angular/router";
import {distinctUntilChanged, filter} from "rxjs";
import {TitleCasePipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";

export interface Breadcrumb {

  label: string;
  url: string;
}

@Component({
  selector: "app-header-bar-breadcrumbs",
  standalone: true,
  imports: [
    RouterLink,
    TitleCasePipe,
    MatIcon
  ],
  templateUrl: "./header-bar-breadcrumbs.component.html"
})
export class HeaderBarBreadcrumbsComponent
  implements OnInit {

  /**
   * Reference to the activated route.
   * This allows access to information about the currently activated route.
   */
  private readonly activatedRoute: ActivatedRoute;

  /**
   * Reference to the router.
   * This allows navigation within the Angular application.
   */
  private readonly router: Router;

  /**
   * Array of breadcrumb items.
   * Stores the breadcrumb items for the current route navigation.
   */
  private _breadcrumbs: Breadcrumb[];

  /**
   * Constructs the HeaderBarBreadcrumbsComponent.
   * Initializes activatedRoute and router references, and builds initial
   * breadcrumbs.
   *
   * @param activatedRoute The ActivatedRoute providing information about
   * the current route.
   * @param router The Router for navigation within the application.
   */
  public constructor(
    activatedRoute: ActivatedRoute,
    router: Router
  ) {
    this.activatedRoute = activatedRoute;
    this.router = router;

    // Build initial breadcrumbs based on the current route.
    this._breadcrumbs = this.buildBreadcrumb(this.activatedRoute.root);
  }

  /**
   * Recursively builds the breadcrumb array based on the current route.
   *
   * This method is adapted from the breadcrumb implementation found in the
   * article "Create a simple breadcrumb in Angular" by Zhiyue Yi.
   * Source: https://dev.to/zhiyueyi/create-a-simple-breadcrumb-in-angular-ag5
   *
   * This method traverses the route hierarchy recursively, constructing
   * breadcrumb items for each segment of the route.
   * It extracts the path from the route configuration, handles dynamic
   * routes by replacing parameters with their actual values, and constructs
   * the URL for each breadcrumb item.
   *
   * @param route The current ActivatedRoute representing the current
   * route segment.
   * @param url The current URL up to the current route segment.
   * @param breadcrumbs An array containing breadcrumb items built so far.
   * @returns An array of breadcrumb items representing the breadcrumb
   * navigation.
   */
  // TODO scenerio?
  private buildBreadcrumb(
    route: ActivatedRoute,
    url: string = "",
    breadcrumbs: Breadcrumb[] = []
  ): Breadcrumb[] {
    // Get the path from the route configuration, if available, or default
    // to an empty string.
    let path = route.routeConfig && route.routeConfig.path
      ? route.routeConfig.path
      : "";

    // Extract the last part of the path to check if it's a dynamic route.
    const lastRoutePart = path.split("/").pop()!;
    const isDynamicRoute = lastRoutePart.startsWith(":");

    // If the route segment is dynamic and a snapshot is available, replace
    // the parameter in the path with its actual value.
    if (isDynamicRoute && !!route.snapshot) {
      const paramName = lastRoutePart.split(":")[1];
      path = path.replace(lastRoutePart, route.snapshot.params[paramName]);
    }

    const label = path.replace("-", " ");

    // Build the next URL by appending the current path to the existing URL.
    const nextUrl = path ? `${url}/${path}` : url;

    // Construct the breadcrumb item for the current route segment.
    const breadcrumb: Breadcrumb = {
      label: label,
      url: nextUrl
    };

    // Only add the breadcrumb item to the array if it has a non-empty label.
    const newBreadcrumbs = breadcrumb.label
      ? [...breadcrumbs, breadcrumb]
      : [...breadcrumbs];

    // Recursively traverse child routes if available.
    if (route.firstChild) {
      return this.buildBreadcrumb(route.firstChild, nextUrl, newBreadcrumbs);
    }

    // Return the array of breadcrumb items.
    return newBreadcrumbs;
  }

  public ngOnInit(): void {
    // Subscribe to router events to update breadcrumbs on navigation changes.
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      distinctUntilChanged()
    ).subscribe(() => {
      this._breadcrumbs = this.buildBreadcrumb(this.activatedRoute.root);
    });
  }

  /**
   * Gets the array of breadcrumbs.
   *
   * @returns The array of breadcrumb items.
   */
  public get breadcrumbs(): Breadcrumb[] {
    return this._breadcrumbs;
  }
}
