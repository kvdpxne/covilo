import {CrimeClassification} from "./crime-classification";
import {CrimePerpetrator} from "./crime-perpetrator";
import {City} from "./city";
import {User} from "./user";

export type CrimeCategory = {
  identifier: string;
  name: string;
}

export type CrimeCategories = CrimeCategory[];

export type Crime2 = {
  identifier: string
  name?: string
  description?: string
  categories: CrimeCategories

  createdDate: Date
  lastModifiedDate?: Date

  when: Date
  place: City
  reporter: User
  confirmed: boolean
}

/**
 *
 */
export type Crime = {
  identifier: string,
  reporter: User,
  date: Date
  place: City
  classification: CrimeClassification[]
  perpetrator?: CrimePerpetrator
  description: string
  confirmed: boolean
}

/**
 *
 */
export type Crimes = Crime[]
