import {Province} from "./province";
import {CapitalType} from "./capital-type";
import {Identifiable} from "../aggregation/identifiable";
import {Nameable} from "../aggregation/nameable";

/**
 *
 */
export class City implements Identifiable, Nameable {

  public readonly identifier: string;
  public readonly name: string;
  public readonly translatableNameKey?: string;
  public readonly nationalName: string;
  public readonly capital: CapitalType;
  public readonly population: number;
  public readonly province: Province;

  public constructor(
    identifier: string,
    name: string,
    nationalName: string,
    capital: CapitalType,
    population: number,
    province: Province
  ) {
    this.identifier = identifier;
    this.name = name;
    this.nationalName = nationalName;
    this.capital = capital;
    this.population = population;
    this.province = province;
  }
}