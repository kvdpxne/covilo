import {Inter} from "next/font/google";
import "./globals.css";
import {Metadata, Viewport} from "next";
import React from "react";

const inter = Inter({subsets: ["latin"]});

/**
 * Generates metadata for the application.
 *
 * @returns Metadata object containing information such as title,
 * description, authors, etc.
 */
export const generateMetadata = (): Metadata => {
  const name = "Covilo";

  return {
    title: {
      template: `%s | ${name}`,
      default: name
    },
    description: "Covilo: Stay informed and safe with real-time"
      + " notifications about crime incidents in your area. Empowering"
      + " communities to stay vigilant and connected.",
    applicationName: name,
    authors: {
      name: "kvdpxne",
      url: "https://github.com/kvdpxne"
    },
    generator: "Next.js",
    robots: {
      googleBot: {
        index: true
      }
    }
  };
};

/**
 * Generates viewport configuration for the application.
 *
 * @returns Viewport object containing themeColor and colorScheme information.
 */
export const generateViewport = (): Viewport => {
  return {
    themeColor: [{
      media: "(prefers-color-scheme: light)",
      color: "white"
    }, {
      media: "(prefers-color-scheme: dark)",
      color: "black"
    }],
    // colorScheme: "dark light"
  };
};

/**
 * Defines the root layout for the application.
 *
 * @param children The child components to be rendered inside the layout.
 * @returns The root layout component.
 */
const RootLayout = (
  {children}: { children: React.ReactNode }
) => {
  return (
    <html lang="en" className="h-full">
      <body className={`${inter.className} h-full`}>
        {children}
      </body>
    </html>
  );
};

/**
 * Exports the RootLayout component as the default export.
 */
export default RootLayout;
