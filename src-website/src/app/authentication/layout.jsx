"use client";

import Footer from "../_components/Footer";

/**
 * Metadata for the authentication layout.
 */
const metadata = {
  title: "Authentication | Covilo",
  description: ""
}

/**
 * Layout component for authentication pages.
 *
 * @param children The children components to render within the layout.
 * @returns JSX.Element representing the authentication layout.
 */
const AuthenticationLayout = ({children}) => (
  <>
    {children}
  </>
)

// export {metadata}

/**
 * Object containing metadata and the authentication layout component.
 */
export default AuthenticationLayout
