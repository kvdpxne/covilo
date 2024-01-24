import {Directive, HostListener} from "@angular/core";
import {NavigationService} from "../services/navigation.service";

@Directive({
  selector: "[navigationBack]",
  standalone: true
})
export class NavigationBackDirective {

  private readonly navigationService: NavigationService;

  public constructor(navigationService: NavigationService) {
    this.navigationService = navigationService;
  }

  @HostListener("click")
  public onClick(): void {
    this.navigationService.back();
  }
}
