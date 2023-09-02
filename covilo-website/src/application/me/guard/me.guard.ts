import {CanActivateFn, Router} from "@angular/router";
import {Observable, tap, throwError} from "rxjs";
import {inject} from "@angular/core";
import {AuthenticationService} from "../../authentication";
import {map} from "rxjs/operators";

export const meGuard: CanActivateFn = (): Observable<boolean> => {
  const router: Router = inject(Router);
  const authenticationService: AuthenticationService = inject(AuthenticationService);

  return authenticationService.isLoggedIn().pipe(
    tap((isLogged: boolean): void => {
      if (!isLogged) {
        router.navigateByUrl("/authentication/login").catch(error => throwError(() => error));
      }
    }),
    map((isLogged: boolean) => isLogged)
  )
};
