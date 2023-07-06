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
  province: Province;
}

/**
 *
 */
export declare type Cities = City[]