import {Province} from "./province"
import {CapitalType} from "./capital-type"

/**
 *
 */
export interface City {
  identifier: string;
  name: string;
  domesticName: string;
  capital: CapitalType;
  population: number;
  province: Province;
}

/**
 *
 */
export declare type Cities = City[]