import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {Observable, tap, throwError} from "rxjs";
import {map} from "rxjs/operators";
import {UserAuthenticationService} from "../../core";

export const authenticationGuard: CanActivateFn = (): Observable<boolean> => {
  const router: Router = inject(Router);
  const authenticationService: UserAuthenticationService = inject(UserAuthenticationService);

  return authenticationService.isLogged().pipe(
    tap((isLogged: boolean): void => {
      if (isLogged) {
        router.navigate(["/"]).catch(error => throwError(error))
      }
    }),
    map((isLogged: boolean) => !isLogged)
  );
};
