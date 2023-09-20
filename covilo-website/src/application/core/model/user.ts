import {Gender} from "./gender";
import {City} from "./city";
import {Auditable} from "../aggregate/auditable";
import {Identifiable} from "../aggregate/identifiable";

export interface User extends Identifiable, Auditable {
  email: string;
  firstName: string;
  lastName: string;
  gender: Gender;
  birthDate: Date;
  livingPlace: City;
}