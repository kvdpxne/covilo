import {Classification} from "./classification";
import {Identifiable} from "../aggregation/identifiable";
import {Nameable} from "../aggregation/nameable";

export class Category implements Identifiable, Nameable {

  /**
   * A random unique identifier.
   */
  public readonly identifier: string;

  /**
   * A unique name.
   */
  public readonly name: string;

  public readonly translatableNameKey: string;

  /**
   * Classification to which a given category is classified.
   */
  public readonly classification: Classification;

  public constructor(
    identifier: string,
    name: string,
    classification: Classification
  ) {
    this.identifier = identifier;
    this.name = name;
    this.translatableNameKey = "core.model.category.".concat(this.name);
    this.classification = classification;
  }
}