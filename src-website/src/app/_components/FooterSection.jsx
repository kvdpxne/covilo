"use client"

import Link from "next/link";

export default function FooterSection({title, items}) {
  return (
    <div className="px-10 mx-10">
      <p className="text-xl">{title}</p>
      <ul className="flex flex-col pt-4 pb-6">
        {items.map(({name, href}) => (
          <li className="py-1" key={name}>
            <Link href={href}>
              {name}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  )
}