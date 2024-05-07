"use client";

import dynamic from "next/dynamic";
import Image from 'next/image'

/**
 * Lazy loads the Newsletter component to improve initial page load performance.
 * The component is excluded from server-side rendering (SSR).
 */
const LazyNewsletter = dynamic(() => import("./_components/Newsletter"), {
  ssr: false
});

const LazyFaq = dynamic(() => import("./_components/Faq"), {
  ssr: false
})

export default function Home() {
  return (
    <main>

      <div className="relative isolate overflow-hidden bg-gray-900 pb-16 pt-16 sm:pb-20">

        <Image src="/fsfs.webp" width={1080} height={1920}
               className="absolute inset-0 -z-10 h-full w-full object-cover"/>

        <div className="mx-auto max-w-7xl px-6 lg:px-8">
          <div className="mx-auto max-w-2xl py-32 sm:py-48 lg:py-56">
            <div className="hidden sm:mb-8 sm:flex sm:justify-center">
              <div
                className="relative rounded-full px-3 py-1 text-sm leading-6 text-gray-400 ring-1 ring-white/10 hover:ring-white/20">
                Announcing our next round of funding.{' '}
                <a href="#" className="font-semibold text-white">
                  <span className="absolute inset-0" aria-hidden="true"/>
                  Read more <span aria-hidden="true">&rarr;</span>
                </a>
              </div>
            </div>
            <div className="text-center">
              <h1
                className="text-4xl font-bold tracking-tight text-white sm:text-6xl">
                Deploy to the cloud with confidence
              </h1>
              <p className="mt-6 text-lg leading-8 text-gray-300">
                Anim aute id magna aliqua ad ad non deserunt sunt. Qui irure qui lorem cupidatat commodo. Elit sunt
                amet fugiat veniam occaecat fugiat aliqua.
              </p>
              <div className="mt-10 flex items-center justify-center gap-x-6">
                <a
                  href="#"
                  className="rounded-md bg-indigo-500 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-400"
                >
                  Get started
                </a>
                <a href="#" className="text-sm font-semibold leading-6 text-white">
                  Live demo <span aria-hidden="true">→</span>
                </a>
              </div>
            </div>
          </div>

        </div>
      </div>


      <LazyNewsletter/>
      <LazyFaq/>

    </main>
  );
}
