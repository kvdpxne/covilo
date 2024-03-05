import {CanActivateFn, Router} from "@angular/router";
import {Observable, tap} from "rxjs";
import {inject} from "@angular/core";
import {AuthenticationService} from "../services/authentication.service";
import {map} from "rxjs/operators";

export const authenticationGuard: CanActivateFn = (): Observable<boolean> => {
  const router = inject(Router);
  const authenticationService = inject(AuthenticationService);

  return authenticationService.isLogged().pipe(
    tap(isLogged => {
      if (!isLogged) {
        router.navigateByUrl("/authentication/login");
      }
    }),
    map(isLogged => !isLogged)
  );
};