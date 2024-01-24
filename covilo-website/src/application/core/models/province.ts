import {Country} from "./country";
import {Identifiable} from "../aggregation/identifiable";
import {Nameable} from "../aggregation/nameable";

/**
 *
 */
export class Province implements Identifiable, Nameable {

  public readonly identifier: string;
  public readonly name: string;
  public readonly translatableNameKey?: string;
  public readonly nationalName: string;
  public readonly country: Country;

  public constructor(
    identifier: string,
    name: string,
    nationalName: string,
    country: Country
  ) {
    this.identifier = identifier;
    this.name = name;
    this.nationalName = nationalName;
    this.country = country;
  }
}