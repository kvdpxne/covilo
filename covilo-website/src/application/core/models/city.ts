import {Province} from "./province"
import {CapitalType} from "./capital-type"

/**
 *
 */
export interface City {
  identifier: string;
  name: string;
  nationalName: string;
  capital: CapitalType;
  population: number;
  province: Province;
}