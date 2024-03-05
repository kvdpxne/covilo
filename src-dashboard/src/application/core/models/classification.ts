import {Identifiable} from "../aggregation/identifiable";
import {Nameable} from "../aggregation/nameable";

export class Classification implements Identifiable, Nameable {

  /**
   * A random unique identifier.
   */
  public readonly identifier: string;

  /**
   * A unique name.
   */
  public readonly name: string;

  public readonly translatableNameKey?: string;

  public constructor(
    identifier: string,
    name: string
  ) {
    this.identifier = identifier;
    this.name = name;
    this.translatableNameKey = "core.model.classification.".concat(this.name);
  }
}