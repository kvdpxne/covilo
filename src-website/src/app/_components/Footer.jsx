"use client"

import FooterSection from "@/app/_components/FooterSection";
import FooterSocials from "@/app/_components/FooterSocials";

const items = [{
  name: "Home",
  href: "/"
}, {
  name: "Report",
  href: "/"
}, {
  name: "Statistics",
  href: "/"
}, {
  name: "Interactive Map",
  href: "/"
}]

export default function Footer() {
  return (
    <footer className="">
      <div className="lg:container mx-auto lg:px-32 px-0 pt-6 pb-8">

        {/* */}
        <div className="flex justify-center w-full">
          <div className="grid grid-cols-3 space-x-12">
            <FooterSection title="Appearance" items={items}/>
            <FooterSection title="Appearance" items={items}/>
            <FooterSection title="Appearance" items={items}/>
          </div>
        </div>

        <div className="flex-col border-t border-t-neutral-5  00 pt-6 pb-4">
          <p>&copy; 2024 Covilo, Inc. All rights reserved.</p>
          <FooterSocials/>
        </div>
      </div>

    </footer>
  )
}