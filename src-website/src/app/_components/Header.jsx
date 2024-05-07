"use client"

import React, {useState} from "react";
import {Bars3Icon} from "@heroicons/react/24/outline";
import {Dialog} from "@headlessui/react";
import {XMarkIcon} from "@heroicons/react/16/solid";
import Link from "next/link";
import LogotypeIcon from "./_icons/LogotypeIcon";

const navigation = [
  {name: 'Home page', href: '/'},
  {name: 'Report', href: '/report'},
  {name: 'Interactive map', href: '/interactive-map'},
  {name: 'Search', href: '/search'},
]

const buttons = [
  {name: "Sign up", href: "/authentication/signup"},
  {name: "Log in", href: "/authentication/login"}
]

/**
 * Represents the logo of the application.
 */
const Logotype = () => (
  <Link href="/"
        className="-m-1.5 p-1.5">
    <LogotypeIcon width="92pt"
                  className="fill-gray-900 dark:fill-white"/>
  </Link>
)

/**
 *
 */
const NavigationListItem = ({name, href}) => (
  <Link href={href}
        key={name.toLowerCase()}
        className="text-sm font-semibold leading-6 text-gray-900 dark:text-white">
    {name}
  </Link>
)

/**
 * Represents a list of navigation links.
 */
const NavigationList = () => (
  <div className="hidden lg:flex lg:gap-x-12">
    {navigation.map(({name, href}) => (
      <NavigationListItem name={name} href={href}/>
    ))}
  </div>
)

/**
 *
 */
const Header = () => {
  //
  const [
    mobileMenuOpen,
    setMobileMenuOpen
  ] = useState(false);

  //
  const openMobileMenu = () => {
    setMobileMenuOpen(true)
  };

  //
  const closeMobileMenu = () => {
    setMobileMenuOpen(false)
  }

  return (
    <header className="absolute inset-x-0 top-0 z-50">
      <nav className="flex items-center justify-between container mx-auto lg:px-32 px-0 pt-6 pb-8">

        {/* Brand logotype */}
        <div className="flex lg:flex-1">
          <Logotype/>
        </div>

        {/* */}
        <div className="flex lg:hidden">
          <button type="button"
                  className="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700"
                  onClick={openMobileMenu}>
            <Bars3Icon className="h-6 w-6"/>
          </button>
        </div>

        {/* */}
        <NavigationList/>

        <div className="hidden lg:flex lg:gap-x-4 lg:flex-1 lg:justify-end">
          {buttons.map(({name, href}) => (
            <Link href={href}
                  key={href}
                  className="text-sm font-semibold leading-6 text-gray-900 dark:text-white">
              {name}
            </Link>
          ))}
        </div>
      </nav>

      <Dialog as="div" className="lg:hidden" open={mobileMenuOpen}
              onClose={setMobileMenuOpen}>
        <Dialog.Panel
          className="fixed inset-y-0 right-0 z-50 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">

          <div className="flex items-center justify-between">

            <Logotype/>

            <button type="button"
                    className="-m-2.5 rounded-md p-2.5 text-gray-700"
                    onClick={closeMobileMenu}>
              <XMarkIcon className="h-6 w-6" aria-hidden="true"/>
            </button>
          </div>

          <div className="mt-6 flow-root">
            <div className="-my-6 divide-y divide-gray-500/10">
              <div className="space-y-2 py-6">
                {navigation.map(({name, href}) => (
                  <Link key={href}
                        href={href}
                        className="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50">
                    {name}
                  </Link>
                ))}
              </div>

              <div className="py-6">
                {buttons.map(({name, href}) => (
                  <Link href={href}
                        key={href}
                        className="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 dark:text-white hover:bg-gray-50">
                    {name}
                  </Link>
                ))}
              </div>

            </div>
          </div>
        </Dialog.Panel>
      </Dialog>
    </header>
  )
}

export default Header