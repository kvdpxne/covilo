import {Directive, HostListener} from "@angular/core";
import {NavigationService} from "../service/navigation.service";

@Directive({
  selector: "[navigationBack]"
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
