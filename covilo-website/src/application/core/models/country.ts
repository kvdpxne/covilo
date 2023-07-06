import {Continent} from "./continent";

/**
 *
 */
export interface Country {
  identifier: string;
  name: string;
  continent: Continent;
}

/**
 * Shortcut for the `Country` collection.
 */
export declare type Countries = Country[]
