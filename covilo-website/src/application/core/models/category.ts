import {Classification} from "./classification";

export interface Category {

  /**
   * A random unique identifier.
   */
  identifier: string;

  /**
   * A unique name.
   */
  name: string;

  /**
   * Classification to which a given category is classified.
   */
  classification: Classification;
}