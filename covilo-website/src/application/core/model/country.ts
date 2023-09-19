import {Continent} from "./continent";
import {AdministrativeDivisionType} from "./administrative-division-type";

/**
 *
 */
export interface Country {
  identifier: string;
  name: string;
  continent: Continent;
  administrativeDivisionType: AdministrativeDivisionType;
}
