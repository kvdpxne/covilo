import {City} from "./city";
import {User} from "./user";
import {Category} from "./category";

export interface Crime {

  /**
   * A random unique identifier.
   */
  identifier: string;

  /**
   * Additional title of the committed crime.
   */
  title?: string;

  /**
   * Additional description of the committed crime.
   */
  description?: string;

  /**
   * Categories into which the crime has been categorized.
   */
  categories: Category[];

  /**
   * The date and time the crime was committed.
   */
  time: Date;

  /**
   * The place where the crime was committed .
   */
  place: City;

  /**
   * The user who reported the crime.
   */
  reporter: User;

  /**
   * Whether the crime is confirmed.
   */
  confirmed: boolean;
}

export type Crimes = Crime[]
