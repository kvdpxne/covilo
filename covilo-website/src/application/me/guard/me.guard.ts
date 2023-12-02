import {CanActivateFn, Router} from "@angular/router";
import {Observable, tap, throwError} from "rxjs";
import {inject} from "@angular/core";
import {UserAuthenticationService} from "../../core";
import {map} from "rxjs/operators";

export const meGuard: CanActivateFn = (): Observable<boolean> => {
  const router: Router = inject(Router);
  const authenticationService: UserAuthenticationService = inject(UserAuthenticationService);

  return authenticationService.isLogged().pipe(
    tap((isLogged: boolean): void => {
      if (!isLogged) {
        router.navigateByUrl("/authentication/login").catch(error => throwError(() => error));
      }
    }),
    map((isLogged: boolean) => isLogged)
  )
};
