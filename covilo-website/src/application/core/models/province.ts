import { Country } from "./country"

/**
 *
 */
export interface Province {
  identifier: string;
  name: string;
  domesticName: string;
  country: Country;
}

/**
 * Shortcut for the `Province` collection.
 */
export declare type Provinces = Province[];
