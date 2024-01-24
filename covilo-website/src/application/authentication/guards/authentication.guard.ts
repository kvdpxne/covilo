import {CanActivateFn} from "@angular/router";
import {inject} from "@angular/core";
import {Observable, tap} from "rxjs";
import {map} from "rxjs/operators";
import {UserAuthenticationService} from "../../core";
import {NavigationService} from "../../shared";

export const authenticationGuard: CanActivateFn = (): Observable<boolean> => {
  const authenticationService: UserAuthenticationService = inject(UserAuthenticationService);
  const navigationService: NavigationService = inject(NavigationService);

  return authenticationService.isLogged().pipe(
    tap((isLogged: boolean): void => {
      if (isLogged) {
        navigationService.navigateToHomePage();
      }
    }),
    map((isLogged: boolean) => !isLogged)
  );
};
