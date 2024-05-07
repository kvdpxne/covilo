"use client"

import {usePathname} from "next/navigation";
import Header from "./_components/Header";
import Footer from "./_components/Footer";

const Template = ({children}) => {
  const path = usePathname()
  const isAuthenticationRelated = path.includes("authentication")

  return (
    <>
      {!isAuthenticationRelated && <Header/>}
      {children}
      <Footer/>
    </>
  )
}

export default Template