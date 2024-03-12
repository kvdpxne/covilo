import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot
} from "@angular/router";
import {inject} from "@angular/core";
import {
  TokenAuthenticationStrategy
} from "../services/token-authentication-strategy";

/**
 * Guards routes based on user authentication status.
 *
 * This guard function checks whether the user is authenticated using a
 * token-based authentication strategy. If the user is authenticated, it
 * permits navigation to the requested route; otherwise, it redirects the
 * user to the login page. If the user is already logged in, it prevents
 * navigation to authentication-related pages and redirects them to their
 * current page.
 *
 * @param _ The activated route snapshot (not used)
 * @param state The router state snapshot
 * @returns True if the user is authenticated and can access the route;
 * otherwise, false.
 */
export const authenticationGuard: CanActivateFn = (
  _: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean => {
  // Inject the token authentication strategy service
  const tokenAuthenticationStrategy = inject(TokenAuthenticationStrategy);

  // Inject the router service
  const router = inject(Router);

  // Check if the user is logged in
  const isLogged = tokenAuthenticationStrategy.isLogged();

  // Check if the current route is related to authentication
  const isAuthenticationPage = state.url.includes("authentication");

  // If the user is logged in,
  // prevent navigation to authentication-related pages
  if (isLogged) {

    // If attempting to access an authentication-related page,
    // redirect to the current page
    if (isAuthenticationPage) {
      router.navigateByUrl(state.url);
      return false;
    }

    // Permit navigation
    return true;
  }

  // Allow access to authentication-related pages for non-authenticated users
  if (isAuthenticationPage) {
    return true;
  }

  // If the user is not logged in, redirect to the login page
  router.navigateByUrl("/authentication/login");

  // Return false to prevent navigation
  return false;
};