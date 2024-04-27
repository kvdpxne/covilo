"use client"

/*
  This example requires some changes to your config:

  ```
  // tailwind.config.js
  module.exports = {
    // ...
    plugins: [
      // ...
      require('@tailwindcss/forms'),
    ],
  }
  ```
*/
import { CalendarDaysIcon, HandRaisedIcon } from '@heroicons/react/24/outline'

export default function Newsletter() {
  return (
    <div className="relative isolate overflow-hidden py-16 sm:py-24 lg:py-32">
      <div className="mx-auto max-w-7xl px-6 lg:px-8">
        <div className="mx-auto grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 lg:max-w-none lg:grid-cols-2">
          <div className="max-w-xl lg:max-w-lg">
            <h2 className="text-3xl font-bold tracking-tight sm:text-4xl">
              Subscribe to our newsletter.
            </h2>
            <p className="mt-4 text-lg leading-8">
              Nostrud amet eu ullamco nisi aute in ad minim nostrud adipisicing
              velit quis. Duis tempor incididunt
              dolore.
            </p>
            <div className="mt-6 flex max-w-md gap-x-4">
              <label htmlFor="email-address" className="sr-only">
                Email address
              </label>
              <input id="email-address"
                     name="email"
                     type="email"
                     autoComplete="email"
                     required
                     className="min-w-0 flex-auto rounded-md border-0 bg-black/5 px-3.5 py-2 shadow-sm ring-1 ring-inset ring-white/10 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                     placeholder="Enter your email"
              />
              <button type="submit"
                      className="flex-none rounded-md bg-indigo-500 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">
                Subscribe
              </button>
            </div>
          </div>
          <dl className="grid grid-cols-1 gap-x-8 gap-y-10 sm:grid-cols-2 lg:pt-2">
            <div className="flex flex-col items-start">
              <div className="rounded-md bg-black/5 p-2 ring-1 ring-black/10">
                <CalendarDaysIcon className="h-6 w-6" aria-hidden="true"/>
              </div>
              <dt className="mt-4 font-semibold">
                Weekly articles
              </dt>
              <dd className="mt-2 leading-7">
                Stay informed with our Weekly Newsletter, delivering insightful crime prevention updates every week, keeping you vigilant.
              </dd>
            </div>
            <div className="flex flex-col items-start">
              <div className="rounded-md bg-black/5 p-2 ring-1 ring-black/10">
                <HandRaisedIcon className="h-6 w-6" aria-hidden="true"/>
              </div>
              <dt className="mt-4 font-semibold">
                No spam
              </dt>
              <dd className="mt-2 leading-7">
                Rest assured, our Newsletter is spam-free, ensuring only relevant crime alerts reach your inbox, free from distractions.
              </dd>
            </div>
          </dl>
        </div>
      </div>
    </div>
  )
}
