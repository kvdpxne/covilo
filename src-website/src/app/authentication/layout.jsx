import NarrowedHeader from "./_components/NarrowedHeader";

/**
 * Layout component for authentication pages.
 *
 * @param children The children components to render within the layout.
 * @returns JSX.Element representing the authentication layout.
 */
const AuthenticationLayout = ({children}) => (
  <>
    <NarrowedHeader/>
    {children}
  </>
)

/**
 * Object containing metadata and the authentication layout component.
 */
export default AuthenticationLayout
