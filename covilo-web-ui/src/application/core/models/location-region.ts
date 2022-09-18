import { LocationCountry } from "./location-country"

export interface LocationRegion {
  key: string,
  domesticName: string,
  country: LocationCountry
}
