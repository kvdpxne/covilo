import Header from "../_components/Header";
import Footer from "../_components/Footer";

const AppearanceLayout = ({children}) => (
  <>
    <Header/>
    {children}
    <Footer/>
  </>
);

export default AppearanceLayout;