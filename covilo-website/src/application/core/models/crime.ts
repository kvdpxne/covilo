import { CrimeClassification } from "./crime-classification"
import { CrimePerpetrator } from "./crime-perpetrator"
import { City } from "./city"

/**
 *
 */
export interface Crime {
  date: Date
  city: City
  classification: CrimeClassification
  perpetrator?: CrimePerpetrator
  description: string
  confirmed: boolean
}

/**
 *
 */
export declare type Crimes = Crime[]
