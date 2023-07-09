import { CrimeClassification } from "./crime-classification"
import { CrimePerpetrator } from "./crime-perpetrator"
import { City } from "./city"
import {User} from "./user";

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
