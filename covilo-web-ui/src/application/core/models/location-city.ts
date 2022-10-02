import { LocationRegion } from "./location-region"
import { CapitalType } from "./capital-type"

export interface LocationCity {
  key: string,
  domesticName: string,
  region: LocationRegion,
  population: number,
  capital: CapitalType
}
