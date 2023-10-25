import {Continent} from "./continent";
import {AdministrativeDivisionType} from "./administrative-division-type";
import {Identifiable} from "../aggregation/identifiable";
import {Nameable} from "../aggregation/nameable";

/**
 *
 */
export class Country implements Identifiable, Nameable {

  public readonly identifier: string;
  public readonly name: string;
  public readonly translatableNameKey?: string;
  public readonly continent: Continent;
  public readonly administrativeDivision: AdministrativeDivisionType;

  public constructor(
    identifier: string,
    name: string,
    continent: Continent,
    administrativeDivision: AdministrativeDivisionType
  ) {
    this.identifier = identifier;
    this.name = name;
    this.continent = continent;
    this.administrativeDivision = administrativeDivision;
  }
}
