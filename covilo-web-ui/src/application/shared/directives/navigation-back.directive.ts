import { Directive, HostListener } from "@angular/core"
import { NavigationService } from "../services/navigation.service"

@Directive({
  selector: "[navigationBack]"
})
export class NavigationBackDirective {

  constructor(private readonly navigationService: NavigationService) {
  }

  @HostListener("click")
  onClick(): void {
    this.navigationService.back()
  }
}
