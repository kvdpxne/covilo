import { CrimeClassification } from "./crime-classification"
import { CrimePerpetrator } from "./crime-perpetrator"
import { LocationCity } from "./location-city"

export interface Crime {
  date: Date
  city: LocationCity
  classification: CrimeClassification,
  perpetrator?: CrimePerpetrator,
  description: string,
  confirmed: boolean
}
