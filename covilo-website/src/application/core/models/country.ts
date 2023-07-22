import {Continent} from "./continent";

/**
 *
 */
export interface Country {
  identifier: string;
  name: string;
  continent: Continent;
}
