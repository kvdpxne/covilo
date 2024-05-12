"use client";

import Header from "./_components/Header";
import Footer from "./_components/Footer";

const RootTemplate = ({children}) => (
  <>
    <Header/>
    {children}
    <Footer/>
  </>
);

export default RootTemplate;