import { Country } from "./country"

/**
 *
 */
export interface Province {
  identifier: string;
  name: string;
  nationalName: string;
  country: Country;
}