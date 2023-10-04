import {Gender} from "./gender";
import {City} from "./city";
import {Auditable} from "../aggregation/auditable";
import {Identifiable} from "../aggregation/identifiable";

export interface User extends Identifiable, Auditable {
  email: string;
  firstName: string;
  lastName: string;
  gender: Gender;
  birthDate: Date;
  livingPlace: City;
}