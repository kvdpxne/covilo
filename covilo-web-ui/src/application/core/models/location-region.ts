import { LocationCountry } from "./location-country"

/**
 *
 */
export interface LocationRegion {
  key: string
  domesticName: string
  country: LocationCountry
}

/**
 * Shortcut for the `LocationRegion` collection.
 */
export declare type LocationRegions = LocationRegion[]
