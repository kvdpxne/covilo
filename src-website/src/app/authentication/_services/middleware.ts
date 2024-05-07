import {NextRequest} from "next/server";

/**
 * Routes that require authentication to access.
 */
const protectedRoutes = [
  "/profile"
];

/**
 * Routes that are publicly accessible without authentication.
 */
const publicRoutes = [
  "/",
  "/search",

  "/forgot-password",
  "/login",
  "/signup"
];

const middleware = async (request: NextRequest) => {
  const path = request.nextUrl.pathname
  const isProtectedRoute = protectedRoutes.includes(path)
  const isPublicRoute = publicRoutes.includes(path)

  // const credentials = inMemoryStorage.get()
}