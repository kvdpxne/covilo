import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: "app-header-bar-breadcrumbs",
  standalone: true,
  imports: [],
  templateUrl: "./header-bar-breadcrumbs.component.html"
})
export class HeaderBarBreadcrumbsComponent
  implements OnInit, OnDestroy {

  private readonly activatedRoute: ActivatedRoute

  private _segments: [{ name: string, url: string}] = [{name: "", url: ""}];

  public constructor(activatedRoute: ActivatedRoute) {
    this.activatedRoute = activatedRoute;
  }

  public get segments() {
    return this._segments;
  }

  public ngOnInit(): void {
    this.activatedRoute.url.subscribe(segment => {
      this._segments.push(...segment.map(it => {
        return {
          name: it.path,
          url: ""
        }
      }))
      console.log(this._segments);
    })
  }

  public ngOnDestroy(): void {

  }
}
