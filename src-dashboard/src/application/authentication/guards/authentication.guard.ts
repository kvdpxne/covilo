import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthenticationTokenStrategy} from "../services/authentication-token-strategy";

/**
 * Authentication guard function for protecting routes based on user authentication status.
 *
 * This guard function checks if the user is authenticated using an authentication token
 * strategy. If the user is authenticated, the function allows navigation to the requested
 * route; otherwise, it redirects the user to the login page.
 *
 * @returns True if the user is authenticated and can access the route; otherwise, false.
 */
export const authenticationGuard: CanActivateFn = (): boolean => {
  // Inject the token authentication strategy service
  const tokenAuthenticationStrategy = inject(AuthenticationTokenStrategy)

  // Check if the user is logged in
  const isLogged: boolean = tokenAuthenticationStrategy.isLogged();

  // If the user is logged in, allow access to the route
  if (isLogged) {
    return true;
  }

  // If the user is not logged in, redirect to the login page
  const router = inject(Router);
  router.navigateByUrl("/authentication/login");

  // Return false to prevent navigation
  return false;
};